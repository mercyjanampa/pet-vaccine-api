package com.petvax.infrastructure.web.response;

import com.petvax.domain.model.Species;

import java.time.LocalDate;

public record PetResponse(
        Long id,
        String name,
        Species species,
        String breed,
        LocalDate birthDate,
        String sex,
        Long ownerId
) {
}