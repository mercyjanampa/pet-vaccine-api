package com.petvax.infrastructure.persistence.adapter;

import com.petvax.application.port.out.SaveVaccinePort;
import com.petvax.domain.model.Vaccine;
import com.petvax.infrastructure.persistence.entity.VaccineEntity;
import com.petvax.infrastructure.persistence.mapper.VaccineMapper;
import com.petvax.infrastructure.persistence.repository.VaccineJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class VaccinePersistenceAdapter implements SaveVaccinePort {

    private final VaccineJpaRepository vaccineRepository;

    public VaccinePersistenceAdapter(VaccineJpaRepository vaccineRepository) {
        this.vaccineRepository = vaccineRepository;
    }

    @Override
    public Vaccine saveVaccine(Vaccine vaccine) {

        // Convierto la vacuna del dominio a una entidad que JPA pueda guardar
        VaccineEntity entity = new VaccineEntity(
                vaccine.getId(),
                vaccine.getName(),
                vaccine.getSpecies(),
                vaccine.getDescription(),
                vaccine.getRecommendedIntervalMonths()
        );

        // Guardo la vacuna en MySQL
        VaccineEntity savedEntity = vaccineRepository.save(entity);

        // Devuelvo nuevamente el modelo del dominio
        return VaccineMapper.toDomain(savedEntity);
    }
}