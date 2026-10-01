package com.petvax.application.port.in;

import com.petvax.domain.model.Pet;
import com.petvax.domain.model.Species;

import java.time.LocalDate;

public interface UpdatePetUseCase {

    Pet update(
            Long id,
            String name,
            Species species,
            String breed,
            LocalDate birthDate,
            String sex,
            Long ownerId
    );
}