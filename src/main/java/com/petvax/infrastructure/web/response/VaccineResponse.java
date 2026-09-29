package com.petvax.infrastructure.web.response;

import com.petvax.domain.model.Species;

public record VaccineResponse(
        Long id,
        String name,
        Species species,
        String description,
        Integer recommendedIntervalMonths
) {
}