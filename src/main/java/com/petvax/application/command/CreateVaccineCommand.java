package com.petvax.application.command;

import com.petvax.domain.model.Species;

public record CreateVaccineCommand(
        String name,
        Species species,
        String description,
        Integer recommendedIntervalMonths
) {
}