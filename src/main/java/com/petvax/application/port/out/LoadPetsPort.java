package com.petvax.application.port.out;

import com.petvax.domain.model.Pet;

import java.util.List;

public interface LoadPetsPort {

    List<Pet> findAllPets();
}