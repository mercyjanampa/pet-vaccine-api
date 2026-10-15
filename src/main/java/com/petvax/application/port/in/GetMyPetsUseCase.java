package com.petvax.application.port.in;

import com.petvax.domain.model.Pet;

import java.util.List;

public interface GetMyPetsUseCase {

    List<Pet> getMyPets(String email);
}