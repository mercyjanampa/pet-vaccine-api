package com.petvax.infrastructure.persistence.adapter;

import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadVaccinePort;
import com.petvax.application.port.out.SaveVaccinationPort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.Vaccination;
import com.petvax.domain.model.Vaccine;
import com.petvax.infrastructure.persistence.entity.PetEntity;
import com.petvax.infrastructure.persistence.entity.VaccinationEntity;
import com.petvax.infrastructure.persistence.entity.VaccineEntity;
import com.petvax.infrastructure.persistence.mapper.PetMapper;
import com.petvax.infrastructure.persistence.mapper.VaccinationMapper;
import com.petvax.infrastructure.persistence.mapper.VaccineMapper;
import com.petvax.infrastructure.persistence.repository.PetJpaRepository;
import com.petvax.infrastructure.persistence.repository.VaccinationJpaRepository;
import com.petvax.infrastructure.persistence.repository.VaccineJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VaccinationPersistenceAdapter
        implements LoadPetPort, LoadVaccinePort, SaveVaccinationPort {

    private final PetJpaRepository petRepository;
    private final VaccineJpaRepository vaccineRepository;
    private final VaccinationJpaRepository vaccinationRepository;

    public VaccinationPersistenceAdapter(
            PetJpaRepository petRepository,
            VaccineJpaRepository vaccineRepository,
            VaccinationJpaRepository vaccinationRepository
    ) {
        this.petRepository = petRepository;
        this.vaccineRepository = vaccineRepository;
        this.vaccinationRepository = vaccinationRepository;
    }

    @Override
    public Optional<Pet> findPetById(Long id) {

        return petRepository.findById(id)
                .map(PetMapper::toDomain);
    }

    @Override
    public Optional<Vaccine> findVaccineById(Long id) {

        return vaccineRepository.findById(id)
                .map(VaccineMapper::toDomain);
    }

    @Override
    public Vaccination save(Vaccination vaccination) {

        PetEntity pet = petRepository.findById(vaccination.getPetId())
                .orElseThrow(() ->
                        new IllegalArgumentException("La mascota no existe")
                );

        VaccineEntity vaccine = vaccineRepository.findById(vaccination.getVaccineId())
                .orElseThrow(() ->
                        new IllegalArgumentException("La vacuna no existe")
                );

        VaccinationEntity entity =
                VaccinationMapper.toEntity(vaccination, pet, vaccine);

        VaccinationEntity savedEntity =
                vaccinationRepository.save(entity);

        return VaccinationMapper.toDomain(savedEntity);
    }
}