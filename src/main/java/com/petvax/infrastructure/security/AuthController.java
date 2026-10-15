package com.petvax.infrastructure.security;

import com.petvax.application.port.in.LoginUseCase;
import com.petvax.application.port.in.RegisterUserUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;

    public AuthController(
            RegisterUserUseCase registerUserUseCase,
            LoginUseCase loginUseCase
    ) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse register(
            @Valid @RequestBody RegisterRequest request
    ) {
        String token = registerUserUseCase.register(
                request.name(),
                request.email(),
                request.password()
        );

        return new TokenResponse(
                token,
                "Bearer",
                900
        );
    }

    @PostMapping("/login")
    public TokenResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        String token = loginUseCase.login(
                request.email(),
                request.password()
        );

        return new TokenResponse(
                token,
                "Bearer",
                900
        );
    }

    public record RegisterRequest(

            @NotBlank
            String name,

            @NotBlank
            @Email
            String email,

            @NotBlank
            @Size(min = 6)
            String password
    ) {
    }

    public record LoginRequest(

            @NotBlank
            @Email
            String email,

            @NotBlank
            String password
    ) {
    }

    public record TokenResponse(
            String accessToken,
            String tokenType,
            long expiresIn
    ) {
    }
}