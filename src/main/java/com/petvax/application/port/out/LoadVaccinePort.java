package com.petvax.application.port.out;

import com.petvax.domain.model.Vaccine;

import java.util.Optional;

public interface LoadVaccinePort {

    Optional<Vaccine> findVaccineById(Long id);
}