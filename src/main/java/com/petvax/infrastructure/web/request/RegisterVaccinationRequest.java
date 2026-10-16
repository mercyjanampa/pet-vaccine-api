package com.petvax.infrastructure.web.request;

import com.petvax.domain.model.VaccinationSource;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegisterVaccinationRequest(

        @NotNull
        Long petId,

        Long vaccineId,

        String customVaccineName,

        @NotNull
        VaccinationSource source,

        @NotNull
        LocalDate applicationDate,

        LocalDate nextDoseDate,

        String notes

) {
}