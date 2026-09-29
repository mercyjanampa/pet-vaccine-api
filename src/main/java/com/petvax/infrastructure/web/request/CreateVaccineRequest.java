package com.petvax.infrastructure.web.request;

import com.petvax.domain.model.Species;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateVaccineRequest(

        @NotBlank(message = "El nombre de la vacuna es obligatorio")
        String name,

        @NotNull(message = "La especie es obligatoria")
        Species species,

        String description,

        @Positive(message = "El intervalo debe ser mayor a cero")
        Integer recommendedIntervalMonths

) {
}