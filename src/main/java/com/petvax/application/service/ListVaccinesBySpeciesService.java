package com.petvax.application.service;

import com.petvax.application.port.in.ListVaccinesBySpeciesUseCase;
import com.petvax.application.port.out.LoadVaccinePort;
import com.petvax.domain.model.Species;
import com.petvax.domain.model.Vaccine;

import java.util.List;

public class ListVaccinesBySpeciesService
        implements ListVaccinesBySpeciesUseCase {

    private final LoadVaccinePort loadVaccinePort;

    public ListVaccinesBySpeciesService(
            LoadVaccinePort loadVaccinePort
    ) {
        this.loadVaccinePort = loadVaccinePort;
    }

    @Override
    public List<Vaccine> findBySpecies(Species species) {

        return loadVaccinePort.findVaccinesBySpecies(species);
    }
}