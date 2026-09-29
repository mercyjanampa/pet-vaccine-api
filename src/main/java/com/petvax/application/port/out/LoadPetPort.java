package com.petvax.application.port.out;

import com.petvax.domain.model.Pet;

import java.util.Optional;

public interface LoadPetPort {

    Optional<Pet> findPetById(Long id);
}