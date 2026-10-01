package com.petvax.infrastructure.web.request;

import com.petvax.domain.model.Species;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdatePetRequest(

        @NotBlank(message = "El nombre de la mascota es obligatorio")
        String name,

        @NotNull(message = "La especie es obligatoria")
        Species species,

        String breed,

        LocalDate birthDate,

        String sex,

        @NotNull(message = "El dueño es obligatorio")
        Long ownerId

) {
}