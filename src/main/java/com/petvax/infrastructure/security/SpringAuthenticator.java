package com.petvax.infrastructure.security;

import com.petvax.application.port.out.AuthenticatorPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class SpringAuthenticator
        implements AuthenticatorPort {

    private final AuthenticationManager authenticationManager;

    public SpringAuthenticator(
            AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void authenticate(
            String email,
            String password
    ) {
        authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken
                        .unauthenticated(email, password)
        );
    }
}