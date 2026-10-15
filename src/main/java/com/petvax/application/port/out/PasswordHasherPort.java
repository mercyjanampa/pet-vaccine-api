package com.petvax.application.port.out;

public interface PasswordHasherPort {

    String hash(String rawPassword);
}