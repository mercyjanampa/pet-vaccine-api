package com.petvax.infrastructure.web.response;

import com.petvax.domain.model.VaccinationSource;
import com.petvax.domain.model.VaccinationStatus;

import java.time.LocalDate;

public record VaccinationResponse(
        Long id,
        Long petId,
        Long vaccineId,
        String customVaccineName,
        VaccinationSource source,
        LocalDate applicationDate,
        LocalDate nextDoseDate,
        String notes,
        VaccinationStatus status,
        String recommendedAction
) {
}