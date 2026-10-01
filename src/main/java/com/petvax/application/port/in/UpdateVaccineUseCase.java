package com.petvax.application.port.in;

import com.petvax.domain.model.Species;
import com.petvax.domain.model.Vaccine;

public interface UpdateVaccineUseCase {

    Vaccine update(
            Long id,
            String name,
            Species species,
            String description,
            Integer recommendedIntervalMonths
    );
}