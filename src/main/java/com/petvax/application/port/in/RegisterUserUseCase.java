package com.petvax.application.port.in;

public interface RegisterUserUseCase {

    String register(
            String name,
            String email,
            String password
    );
}