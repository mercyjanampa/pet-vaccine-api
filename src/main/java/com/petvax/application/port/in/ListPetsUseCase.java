package com.petvax.application.port.in;

import com.petvax.domain.model.Pet;

import java.util.List;

public interface ListPetsUseCase {

    List<Pet> findAll();
}