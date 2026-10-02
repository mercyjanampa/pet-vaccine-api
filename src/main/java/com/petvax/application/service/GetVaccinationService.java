package com.petvax.application.service;

import com.petvax.application.port.in.GetVaccinationUseCase;
import com.petvax.application.port.out.LoadVaccinationsPort;
import com.petvax.domain.model.Vaccination;

public class GetVaccinationService implements GetVaccinationUseCase {

    private final LoadVaccinationsPort loadVaccinationsPort;

    public GetVaccinationService(
            LoadVaccinationsPort loadVaccinationsPort
    ) {
        this.loadVaccinationsPort = loadVaccinationsPort;
    }

    @Override
    public Vaccination findById(Long id) {

        return loadVaccinationsPort.findVaccinationById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La vacunación no existe"
                        )
                );
    }
}