package com.petvax.application.port.in;

import com.petvax.domain.model.Species;
import com.petvax.domain.model.Vaccine;

import java.util.List;

public interface ListVaccinesBySpeciesUseCase {

    List<Vaccine> findBySpecies(Species species);
}