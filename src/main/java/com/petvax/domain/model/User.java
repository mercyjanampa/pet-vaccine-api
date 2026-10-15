package com.petvax.domain.model;

public class User {

    private final Long id;
    private final String name;
    private final String email;
    private final String password;
    private final Role role;
    private final Long ownerId;

    public User(
            Long id,
            String name,
            String email,
            String password,
            Role role,
            Long ownerId
    ) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio"
            );
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "El correo es obligatorio"
            );
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(
                    "La contraseña es obligatoria"
            );
        }

        if (role == null) {
            throw new IllegalArgumentException(
                    "El rol es obligatorio"
            );
        }

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public Long getOwnerId() {
        return ownerId;
    }
}