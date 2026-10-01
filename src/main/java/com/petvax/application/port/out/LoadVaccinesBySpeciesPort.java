package com.petvax.application.port.out;

import com.petvax.domain.model.Species;
import com.petvax.domain.model.Vaccine;

import java.util.List;

public interface LoadVaccinesBySpeciesPort {

    List<Vaccine> findVaccinesBySpecies(Species species);
}