package com.petvax.application.port.out;

public interface CheckUserExistsPort {

    boolean existsByEmail(String email);
}