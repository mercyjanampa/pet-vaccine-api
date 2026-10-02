package com.petvax.application.port.out;

import com.petvax.domain.model.Vaccination;

import java.util.List;
import java.util.Optional;

public interface LoadVaccinationsPort {

    Optional<Vaccination> findVaccinationById(Long id);

    List<Vaccination> findAllVaccinations();

    List<Vaccination> findVaccinationsByPetId(Long petId);

    List<Vaccination> findExpiredVaccinations();
}