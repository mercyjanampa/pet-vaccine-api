package com.petvax.infrastructure.persistence.mapper;

import com.petvax.domain.model.Vaccination;
import com.petvax.infrastructure.persistence.entity.PetEntity;
import com.petvax.infrastructure.persistence.entity.VaccinationEntity;
import com.petvax.infrastructure.persistence.entity.VaccineEntity;

public class VaccinationMapper {

    private VaccinationMapper() {
    }

    public static VaccinationEntity toEntity(
            Vaccination vaccination,
            PetEntity pet,
            VaccineEntity vaccine
    ) {

        return new VaccinationEntity(
                vaccination.getId(),
                pet,
                vaccine,
                vaccination.getApplicationDate(),
                vaccination.getNextDoseDate(),
                vaccination.getNotes()
        );
    }

    public static Vaccination toDomain(VaccinationEntity entity) {

        return new Vaccination(
                entity.getId(),
                entity.getPet().getId(),
                entity.getVaccine().getId(),
                entity.getApplicationDate(),
                entity.getNextDoseDate(),
                entity.getNotes()
        );
    }
}