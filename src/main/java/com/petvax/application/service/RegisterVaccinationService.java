package com.petvax.application.service;

import com.petvax.application.command.RegisterVaccinationCommand;
import com.petvax.application.port.in.RegisterVaccinationUseCase;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadVaccinePort;
import com.petvax.application.port.out.SaveVaccinationPort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.Vaccination;
import com.petvax.domain.model.Vaccine;

public class RegisterVaccinationService implements RegisterVaccinationUseCase {

    private final LoadPetPort loadPetPort;
    private final LoadVaccinePort loadVaccinePort;
    private final SaveVaccinationPort saveVaccinationPort;

    public RegisterVaccinationService(
            LoadPetPort loadPetPort,
            LoadVaccinePort loadVaccinePort,
            SaveVaccinationPort saveVaccinationPort) {

        this.loadPetPort = loadPetPort;
        this.loadVaccinePort = loadVaccinePort;
        this.saveVaccinationPort = saveVaccinationPort;
    }

    @Override
    public Vaccination register(RegisterVaccinationCommand command) {

        Pet pet = loadPetPort.findPetById(command.petId())
                .orElseThrow(() ->
                        new IllegalArgumentException("La mascota no existe")
                );

        Vaccine vaccine = loadVaccinePort.findVaccineById(command.vaccineId())
                .orElseThrow(() ->
                        new IllegalArgumentException("La vacuna no existe")
                );

        if (pet.getSpecies() != vaccine.getSpecies()) {
            throw new IllegalArgumentException(
                    "La vacuna no corresponde a la especie de la mascota"
            );
        }

        Vaccination vaccination = new Vaccination(
                null,
                command.petId(),
                command.vaccineId(),
                command.applicationDate(),
                command.nextDoseDate(),
                command.notes()
        );

        return saveVaccinationPort.save(vaccination);
    }
}