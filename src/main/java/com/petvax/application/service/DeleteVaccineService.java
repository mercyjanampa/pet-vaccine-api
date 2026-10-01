package com.petvax.application.service;

import com.petvax.application.port.in.DeleteVaccineUseCase;
import com.petvax.application.port.out.DeleteVaccinePort;
import com.petvax.application.port.out.LoadVaccinePort;

public class DeleteVaccineService implements DeleteVaccineUseCase {

    private final LoadVaccinePort loadVaccinePort;
    private final DeleteVaccinePort deleteVaccinePort;

    public DeleteVaccineService(
            LoadVaccinePort loadVaccinePort,
            DeleteVaccinePort deleteVaccinePort
    ) {
        this.loadVaccinePort = loadVaccinePort;
        this.deleteVaccinePort = deleteVaccinePort;
    }

    @Override
    public void delete(Long id) {

        loadVaccinePort.findVaccineById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("La vacuna no existe")
                );

        deleteVaccinePort.deleteVaccineById(id);
    }
}