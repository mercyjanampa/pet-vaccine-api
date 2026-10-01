package com.petvax.infrastructure.persistence.adapter;

import com.petvax.application.port.out.SaveVaccinePort;
import com.petvax.domain.model.Vaccine;
import com.petvax.infrastructure.persistence.entity.VaccineEntity;
import com.petvax.infrastructure.persistence.mapper.VaccineMapper;
import com.petvax.infrastructure.persistence.repository.VaccineJpaRepository;
import org.springframework.stereotype.Component;
import com.petvax.application.port.out.LoadVaccinesPort;
import java.util.List;
import com.petvax.application.port.out.DeleteVaccinePort;
import com.petvax.application.port.out.LoadVaccinePort;
import com.petvax.application.port.out.LoadVaccinesPort;
import com.petvax.application.port.out.SaveVaccinePort;
import java.util.Optional;
import com.petvax.application.port.out.LoadVaccinesBySpeciesPort;
import com.petvax.domain.model.Species;

@Component
public class VaccinePersistenceAdapter
        implements SaveVaccinePort,
        LoadVaccinesPort,
        LoadVaccinePort,
        DeleteVaccinePort,
        LoadVaccinesBySpeciesPort {

    private final VaccineJpaRepository vaccineRepository;

    public VaccinePersistenceAdapter(VaccineJpaRepository vaccineRepository) {
        this.vaccineRepository = vaccineRepository;
    }

    @Override
    public Vaccine saveVaccine(Vaccine vaccine) {

        VaccineEntity entity = new VaccineEntity(
                vaccine.getId(),
                vaccine.getName(),
                vaccine.getSpecies(),
                vaccine.getDescription(),
                vaccine.getRecommendedIntervalMonths()
        );

        VaccineEntity savedEntity = vaccineRepository.save(entity);

        return VaccineMapper.toDomain(savedEntity);
    }
    @Override
    public List<Vaccine> findAllVaccines() {

        return vaccineRepository.findAll()
                .stream()
                .map(VaccineMapper::toDomain)
                .toList();
    }
    @Override
    public Optional<Vaccine> findVaccineById(Long id) {

        return vaccineRepository.findById(id)
                .map(VaccineMapper::toDomain);
    }

    @Override
    public void deleteVaccineById(Long id) {

        vaccineRepository.deleteById(id);
    }
    @Override
    public List<Vaccine> findVaccinesBySpecies(Species species) {

        return vaccineRepository.findBySpecies(species)
                .stream()
                .map(VaccineMapper::toDomain)
                .toList();
    }
}