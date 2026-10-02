package com.petvax.application.service;

import com.petvax.application.port.in.ListVaccinationsUseCase;
import com.petvax.application.port.out.LoadVaccinationsPort;
import com.petvax.domain.model.Vaccination;

import java.util.List;

public class ListVaccinationsService implements ListVaccinationsUseCase {

    private final LoadVaccinationsPort loadVaccinationsPort;

    public ListVaccinationsService(
            LoadVaccinationsPort loadVaccinationsPort
    ) {
        this.loadVaccinationsPort = loadVaccinationsPort;
    }

    @Override
    public List<Vaccination> findAll() {

        return loadVaccinationsPort.findAllVaccinations();
    }
}