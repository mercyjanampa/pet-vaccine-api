package com.petvax.infrastructure.web.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegisterVaccinationRequest(

        @NotNull
        Long petId,

        @NotNull
        Long vaccineId,

        @NotNull
        LocalDate applicationDate,

        LocalDate nextDoseDate,

        String notes

) {
}