package com.petvax.application.service;

import com.petvax.application.port.in.UpdateVaccineUseCase;
import com.petvax.application.port.out.LoadVaccinePort;
import com.petvax.application.port.out.SaveVaccinePort;
import com.petvax.domain.model.Species;
import com.petvax.domain.model.Vaccine;

public class UpdateVaccineService implements UpdateVaccineUseCase {

    private final LoadVaccinePort loadVaccinePort;
    private final SaveVaccinePort saveVaccinePort;

    public UpdateVaccineService(
            LoadVaccinePort loadVaccinePort,
            SaveVaccinePort saveVaccinePort
    ) {
        this.loadVaccinePort = loadVaccinePort;
        this.saveVaccinePort = saveVaccinePort;
    }

    @Override
    public Vaccine update(
            Long id,
            String name,
            Species species,
            String description,
            Integer recommendedIntervalMonths
    ) {

        loadVaccinePort.findVaccineById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("La vacuna no existe")
                );

        Vaccine updatedVaccine = new Vaccine(
                id,
                name,
                species,
                description,
                recommendedIntervalMonths
        );

        return saveVaccinePort.saveVaccine(updatedVaccine);
    }
}