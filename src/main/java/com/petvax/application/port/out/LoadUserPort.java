package com.petvax.application.port.out;

import com.petvax.domain.model.User;

import java.util.Optional;

public interface LoadUserPort {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}