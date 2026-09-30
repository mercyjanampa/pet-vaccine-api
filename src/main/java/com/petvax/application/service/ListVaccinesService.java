package com.petvax.application.service;

import com.petvax.application.port.in.ListVaccinesUseCase;
import com.petvax.application.port.out.LoadVaccinesPort;
import com.petvax.domain.model.Vaccine;

import java.util.List;

public class ListVaccinesService implements ListVaccinesUseCase {

    private final LoadVaccinesPort loadVaccinesPort;

    public ListVaccinesService(LoadVaccinesPort loadVaccinesPort) {
        this.loadVaccinesPort = loadVaccinesPort;
    }

    @Override
    public List<Vaccine> findAll() {

        return loadVaccinesPort.findAllVaccines();
    }
}