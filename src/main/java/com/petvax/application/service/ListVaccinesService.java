package com.petvax.application.service;

import com.petvax.application.port.in.ListVaccinesUseCase;
import com.petvax.application.port.out.LoadVaccinePort;
import com.petvax.domain.model.Vaccine;

import java.util.List;

public class ListVaccinesService implements ListVaccinesUseCase {

    private final LoadVaccinePort loadVaccinePort;

    public ListVaccinesService(LoadVaccinePort loadVaccinePort) {
        this.loadVaccinePort = loadVaccinePort;
    }

    @Override
    public List<Vaccine> findAll() {

        return loadVaccinePort.findAllVaccines();
    }
}