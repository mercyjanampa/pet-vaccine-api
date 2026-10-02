package com.petvax.application.port.out;

import com.petvax.domain.model.Species;
import com.petvax.domain.model.Vaccine;

import java.util.List;
import java.util.Optional;

public interface LoadVaccinePort {

    // Busca una vacuna por su id
    Optional<Vaccine> findVaccineById(Long id);

    // Consulta todas las vacunas
    List<Vaccine> findAllVaccines();

    // Consulta vacunas según la especie
    List<Vaccine> findVaccinesBySpecies(Species species);
}