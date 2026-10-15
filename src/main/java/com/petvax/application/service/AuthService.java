package com.petvax.application.service;

import com.petvax.application.port.in.LoginUseCase;
import com.petvax.application.port.in.RegisterUserUseCase;
import com.petvax.application.port.out.AuthenticatorPort;
import com.petvax.application.port.out.CheckUserExistsPort;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.application.port.out.PasswordHasherPort;
import com.petvax.application.port.out.SaveUserPort;
import com.petvax.application.port.out.TokenIssuerPort;
import com.petvax.domain.model.Role;
import com.petvax.domain.model.User;
import com.petvax.application.port.out.SaveOwnerPort;
import com.petvax.domain.model.Owner;

public class AuthService
        implements RegisterUserUseCase, LoginUseCase {

    private final SaveUserPort saveUserPort;
    private final LoadUserPort loadUserPort;
    private final CheckUserExistsPort checkUserExistsPort;
    private final PasswordHasherPort passwordHasherPort;
    private final AuthenticatorPort authenticatorPort;
    private final TokenIssuerPort tokenIssuerPort;
    private final SaveOwnerPort saveOwnerPort;

    public AuthService(
            SaveUserPort saveUserPort,
            LoadUserPort loadUserPort,
            CheckUserExistsPort checkUserExistsPort,
            SaveOwnerPort saveOwnerPort,
            PasswordHasherPort passwordHasherPort,
            AuthenticatorPort authenticatorPort,
            TokenIssuerPort tokenIssuerPort
    ) {
        this.saveUserPort = saveUserPort;
        this.loadUserPort = loadUserPort;
        this.checkUserExistsPort = checkUserExistsPort;
        this.saveOwnerPort = saveOwnerPort;
        this.passwordHasherPort = passwordHasherPort;
        this.authenticatorPort = authenticatorPort;
        this.tokenIssuerPort = tokenIssuerPort;
    }

    @Override
    public String register(
            String name,
            String email,
            String password
    ) {

        if (checkUserExistsPort.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "Ya existe un usuario con ese correo"
            );
        }

        // Primero creamos al propietario
        Owner owner = new Owner(
                null,
                name,
                email,
                null
        );

        Owner savedOwner =
                saveOwnerPort.saveOwner(owner);

        // La contraseña nunca se guarda directamente
        String passwordHash =
                passwordHasherPort.hash(password);

        // Ahora la cuenta queda asociada al propietario
        User user = new User(
                null,
                name,
                email,
                passwordHash,
                Role.USER,
                savedOwner.getId()
        );

        User savedUser =
                saveUserPort.save(user);

        return tokenIssuerPort.issue(
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    @Override
    public String login(
            String email,
            String password
    ) {
        authenticatorPort.authenticate(
                email,
                password
        );

        User user = loadUserPort.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuario no encontrado"
                        )
                );

        return tokenIssuerPort.issue(
                user.getEmail(),
                user.getRole()
        );
    }
}