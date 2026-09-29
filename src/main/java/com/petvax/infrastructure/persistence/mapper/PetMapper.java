package com.petvax.infrastructure.persistence.mapper;

import com.petvax.domain.model.Pet;
import com.petvax.infrastructure.persistence.entity.PetEntity;

public class PetMapper {

    private PetMapper() {
    }

    public static Pet toDomain(PetEntity entity) {

        return new Pet(
                entity.getId(),
                entity.getName(),
                entity.getSpecies(),
                entity.getBreed(),
                entity.getBirthDate(),
                entity.getSex(),
                entity.getOwner().getId()
        );
    }
}