package com.petvax.application.port.out;

public interface AuthenticatorPort {

    void authenticate(String email, String password);
}