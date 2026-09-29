package com.petvax.application.port.out;

import com.petvax.domain.model.Owner;

import java.util.Optional;

public interface LoadOwnerPort {

    // Busca al dueño antes de asociarle una mascota
    Optional<Owner> findOwnerById(Long id);
}