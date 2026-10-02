package com.petvax.application.port.in;

import com.petvax.domain.model.Vaccination;

import java.time.LocalDate;

public interface UpdateVaccinationUseCase {

    Vaccination update(
            Long id,
            Long petId,
            Long vaccineId,
            LocalDate applicationDate,
            LocalDate nextDoseDate,
            String notes
    );
}