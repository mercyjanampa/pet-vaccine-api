package com.petvax.application.service;

import com.petvax.application.port.in.DeleteVaccinationUseCase;
import com.petvax.application.port.out.DeleteVaccinationPort;
import com.petvax.application.port.out.LoadVaccinationsPort;

public class DeleteVaccinationService
        implements DeleteVaccinationUseCase {

    private final LoadVaccinationsPort loadVaccinationsPort;
    private final DeleteVaccinationPort deleteVaccinationPort;

    public DeleteVaccinationService(
            LoadVaccinationsPort loadVaccinationsPort,
            DeleteVaccinationPort deleteVaccinationPort
    ) {
        this.loadVaccinationsPort = loadVaccinationsPort;
        this.deleteVaccinationPort = deleteVaccinationPort;
    }

    @Override
    public void delete(Long id) {

        loadVaccinationsPort.findVaccinationById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La vacunación no existe"
                        )
                );

        deleteVaccinationPort.deleteVaccinationById(id);
    }
}