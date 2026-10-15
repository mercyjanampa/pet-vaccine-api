package com.petvax.application.port.in;

public interface LoginUseCase {

    String login(
            String email,
            String password
    );
}