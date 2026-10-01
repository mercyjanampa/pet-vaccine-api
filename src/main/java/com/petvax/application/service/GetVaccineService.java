package com.petvax.application.service;

import com.petvax.application.port.in.GetVaccineUseCase;
import com.petvax.application.port.out.LoadVaccinePort;
import com.petvax.domain.model.Vaccine;

public class GetVaccineService implements GetVaccineUseCase {

    private final LoadVaccinePort loadVaccinePort;

    public GetVaccineService(LoadVaccinePort loadVaccinePort) {
        this.loadVaccinePort = loadVaccinePort;
    }

    @Override
    public Vaccine findById(Long id) {

        return loadVaccinePort.findVaccineById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("La vacuna no existe")
                );
    }
}