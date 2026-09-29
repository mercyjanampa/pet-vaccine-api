package com.petvax.application.service;

import com.petvax.application.command.CreateVaccineCommand;
import com.petvax.application.port.in.CreateVaccineUseCase;
import com.petvax.application.port.out.SaveVaccinePort;
import com.petvax.domain.model.Vaccine;

public class CreateVaccineService implements CreateVaccineUseCase {

    private final SaveVaccinePort saveVaccinePort;

    public CreateVaccineService(SaveVaccinePort saveVaccinePort) {
        this.saveVaccinePort = saveVaccinePort;
    }

    @Override
    public Vaccine create(CreateVaccineCommand command) {

        // Creo la vacuna con los datos recibidos
        Vaccine vaccine = new Vaccine(
                null,
                command.name(),
                command.species(),
                command.description(),
                command.recommendedIntervalMonths()
        );

        // La envío al puerto encargado de guardarla
        return saveVaccinePort.saveVaccine(vaccine);
    }
}