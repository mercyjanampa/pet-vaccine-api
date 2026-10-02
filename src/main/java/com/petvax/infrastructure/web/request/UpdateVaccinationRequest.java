package com.petvax.infrastructure.web.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateVaccinationRequest(

        @NotNull(message = "La mascota es obligatoria")
        Long petId,

        @NotNull(message = "La vacuna es obligatoria")
        Long vaccineId,

        @NotNull(message = "La fecha de aplicación es obligatoria")
        LocalDate applicationDate,

        LocalDate nextDoseDate,

        String notes

) {
}