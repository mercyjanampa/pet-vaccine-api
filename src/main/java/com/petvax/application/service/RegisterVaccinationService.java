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

        // Primero verifico que la mascota realmente exista
        Pet pet = loadPetPort.findById(command.petId())
                .orElseThrow(() ->
                        new IllegalArgumentException("La mascota no existe")
                );

        // Luego verifico que la vacuna también exista
        Vaccine vaccine = loadVaccinePort.findById(command.vaccineId())
                .orElseThrow(() ->
                        new IllegalArgumentException("La vacuna no existe")
                );

        // Evito registrar una vacuna que no corresponde a la especie
        if (pet.getSpecies() != vaccine.getSpecies()) {
            throw new IllegalArgumentException(
                    "La vacuna no corresponde a la especie de la mascota"
            );
        }

        // Creo la vacunación con los datos recibidos
        Vaccination vaccination = new Vaccination(
                null,
                command.petId(),
                command.vaccineId(),
                command.applicationDate(),
                command.nextDoseDate(),
                command.notes()
        );

        // Finalmente guardo la vacunación
        return saveVaccinationPort.save(vaccination);
    }
}