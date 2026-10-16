package com.petvax.application.command;

import com.petvax.domain.model.VaccinationSource;

import java.time.LocalDate;

public record RegisterVaccinationCommand(
        Long petId,
        Long vaccineId,
        String customVaccineName,
        VaccinationSource source,
        LocalDate applicationDate,
        LocalDate nextDoseDate,
        String notes
) {
}