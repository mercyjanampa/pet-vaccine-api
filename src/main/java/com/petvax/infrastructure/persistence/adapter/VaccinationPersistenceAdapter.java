package com.petvax.infrastructure.persistence.adapter;

import com.petvax.application.port.out.SaveVaccinationPort;
import com.petvax.domain.model.Vaccination;
import com.petvax.infrastructure.persistence.entity.PetEntity;
import com.petvax.infrastructure.persistence.entity.VaccinationEntity;
import com.petvax.infrastructure.persistence.entity.VaccineEntity;
import com.petvax.infrastructure.persistence.mapper.VaccinationMapper;
import com.petvax.infrastructure.persistence.repository.PetJpaRepository;
import com.petvax.infrastructure.persistence.repository.VaccinationJpaRepository;
import com.petvax.infrastructure.persistence.repository.VaccineJpaRepository;
import org.springframework.stereotype.Component;
import com.petvax.application.port.out.LoadVaccinationsPort;
import java.util.Optional;
import java.time.LocalDate;
import java.util.List;
import com.petvax.application.port.out.DeleteVaccinationPort;
import com.petvax.application.port.out.CheckVaccinationExistsPort;


@Component
public class VaccinationPersistenceAdapter
        implements SaveVaccinationPort,
        LoadVaccinationsPort,
        DeleteVaccinationPort,
        CheckVaccinationExistsPort {

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
    @Override
    public Optional<Vaccination> findVaccinationById(Long id) {

        return vaccinationRepository.findById(id)
                .map(VaccinationMapper::toDomain);
    }

    @Override
    public List<Vaccination> findAllVaccinations() {

        return vaccinationRepository.findAll()
                .stream()
                .map(VaccinationMapper::toDomain)
                .toList();
    }

    @Override
    public List<Vaccination> findVaccinationsByPetId(Long petId) {

        return vaccinationRepository.findByPetId(petId)
                .stream()
                .map(VaccinationMapper::toDomain)
                .toList();
    }

    @Override
    public List<Vaccination> findExpiredVaccinations() {

        return vaccinationRepository.findExpiredVaccinations(
                        LocalDate.now()
                )
                .stream()
                .map(VaccinationMapper::toDomain)
                .toList();
    }
    @Override
    public void deleteVaccinationById(Long id) {

        vaccinationRepository.deleteById(id);
    }
    @Override
    public boolean existsByPetIdAndVaccineIdAndApplicationDate(
            Long petId,
            Long vaccineId,
            LocalDate applicationDate
    ) {
        return vaccinationRepository
                .existsByPetIdAndVaccineIdAndApplicationDate(
                        petId,
                        vaccineId,
                        applicationDate
                );
    }
}