package com.petvax.infrastructure.persistence.mapper;

import com.petvax.domain.model.Vaccine;
import com.petvax.infrastructure.persistence.entity.VaccineEntity;

public class VaccineMapper {

    private VaccineMapper() {
    }

    public static Vaccine toDomain(VaccineEntity entity) {

        return new Vaccine(
                entity.getId(),
                entity.getName(),
                entity.getSpecies(),
                entity.getDescription(),
                entity.getRecommendedIntervalMonths()
        );
    }
}