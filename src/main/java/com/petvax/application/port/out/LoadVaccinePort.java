package com.petvax.application.port.out;

import com.petvax.domain.model.Vaccine;

import java.util.Optional;

public interface LoadVaccinePort {

    // Busca una vacuna por su id
    Optional<Vaccine> findById(Long id);
}