package com.petvax.application.port.out;

import com.petvax.domain.model.Pet;

import java.util.List;
import java.util.Optional;

public interface LoadPetPort {

    // Busca una mascota específica
    Optional<Pet> findPetById(Long id);

    // Consulta todas las mascotas
    List<Pet> findAllPets();
}