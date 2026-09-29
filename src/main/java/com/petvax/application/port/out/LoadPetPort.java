package com.petvax.application.port.out;

import com.petvax.domain.model.Pet;

import java.util.Optional;

public interface LoadPetPort {

    // Busca una mascota por su id
    Optional<Pet> findById(Long id);
}