package com.petvax.infrastructure.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(
            AuthenticationManager authenticationManager,
            TokenService tokenService
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public TokenResponse login(
            @RequestBody LoginRequest request
    ) {

        Authentication auth =
                authenticationManager.authenticate(
                        UsernamePasswordAuthenticationToken
                                .unauthenticated(
                                        request.username(),
                                        request.password()
                                )
                );

        return new TokenResponse(
                tokenService.generate(auth),
                "Bearer",
                900
        );
    }

    public record LoginRequest(
            String username,
            String password
    ) {}

    public record TokenResponse(
            String accessToken,
            String tokenType,
            long expiresIn
    ) {}
}