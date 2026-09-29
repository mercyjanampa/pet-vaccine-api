package com.petvax.application.command;

import com.petvax.domain.model.Species;

import java.time.LocalDate;

public record CreatePetCommand(
        String name,
        Species species,
        String breed,
        LocalDate birthDate,
        String sex,
        Long ownerId
) {
}