package com.petvax.infrastructure.persistence.mapper;

import com.petvax.domain.model.Owner;
import com.petvax.infrastructure.persistence.entity.OwnerEntity;

public class OwnerMapper {

    private OwnerMapper() {
    }

    public static OwnerEntity toEntity(Owner owner) {

        return new OwnerEntity(
                owner.getId(),
                owner.getName(),
                owner.getEmail(),
                owner.getPhone()
        );
    }

    public static Owner toDomain(OwnerEntity entity) {

        return new Owner(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhone()
        );
    }
}