package com.petvax.application.service;

import com.petvax.application.port.in.ListExpiredVaccinationsUseCase;
import com.petvax.application.port.out.LoadVaccinationsPort;
import com.petvax.domain.model.Vaccination;

import java.util.List;

public class ListExpiredVaccinationsService
        implements ListExpiredVaccinationsUseCase {

    private final LoadVaccinationsPort loadVaccinationsPort;

    public ListExpiredVaccinationsService(
            LoadVaccinationsPort loadVaccinationsPort
    ) {
        this.loadVaccinationsPort = loadVaccinationsPort;
    }

    @Override
    public List<Vaccination> findExpired() {

        return loadVaccinationsPort.findExpiredVaccinations();
    }
}