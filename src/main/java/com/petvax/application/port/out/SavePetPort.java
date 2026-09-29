package com.petvax.application.port.out;

import com.petvax.domain.model.Pet;

public interface SavePetPort {

    Pet savePet(Pet pet);
}