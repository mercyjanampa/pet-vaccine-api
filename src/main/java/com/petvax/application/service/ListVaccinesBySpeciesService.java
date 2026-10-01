package com.petvax.application.service;

import com.petvax.application.port.in.ListVaccinesBySpeciesUseCase;
import com.petvax.application.port.out.LoadVaccinesBySpeciesPort;
import com.petvax.domain.model.Species;
import com.petvax.domain.model.Vaccine;

import java.util.List;

public class ListVaccinesBySpeciesService
        implements ListVaccinesBySpeciesUseCase {

    private final LoadVaccinesBySpeciesPort loadVaccinesBySpeciesPort;

    public ListVaccinesBySpeciesService(
            LoadVaccinesBySpeciesPort loadVaccinesBySpeciesPort
    ) {
        this.loadVaccinesBySpeciesPort = loadVaccinesBySpeciesPort;
    }

    @Override
    public List<Vaccine> findBySpecies(Species species) {

        return loadVaccinesBySpeciesPort.findVaccinesBySpecies(species);
    }
}