package com.petvax.application.service;

import com.petvax.application.command.RegisterVaccinationCommand;
import com.petvax.application.port.in.RegisterVaccinationUseCase;
import com.petvax.application.port.out.CheckVaccinationExistsPort;
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
    private final CheckVaccinationExistsPort checkVaccinationExistsPort;

    public RegisterVaccinationService(
            LoadPetPort loadPetPort,
            LoadVaccinePort loadVaccinePort,
            SaveVaccinationPort saveVaccinationPort,
            CheckVaccinationExistsPort checkVaccinationExistsPort
    ) {
        this.loadPetPort = loadPetPort;
        this.loadVaccinePort = loadVaccinePort;
        this.saveVaccinationPort = saveVaccinationPort;
        this.checkVaccinationExistsPort = checkVaccinationExistsPort;
    }

    @Override
    public Vaccination register(RegisterVaccinationCommand command) {

        // Verifico que la mascota exista
        Pet pet = loadPetPort.findPetById(command.petId())
                .orElseThrow(() ->
                        new IllegalArgumentException("La mascota no existe")
                );

        // Verifico que la vacuna exista
        Vaccine vaccine = loadVaccinePort.findVaccineById(command.vaccineId())
                .orElseThrow(() ->
                        new IllegalArgumentException("La vacuna no existe")
                );

        // La vacuna debe corresponder a la especie de la mascota
        if (pet.getSpecies() != vaccine.getSpecies()) {
            throw new IllegalArgumentException(
                    "La vacuna no corresponde a la especie de la mascota"
            );
        }

        // El dominio valida las fechas y datos obligatorios
        Vaccination vaccination = new Vaccination(
                null,
                command.petId(),
                command.vaccineId(),
                command.applicationDate(),
                command.nextDoseDate(),
                command.notes()
        );

        // Evito registrar dos veces la misma vacunación
        if (checkVaccinationExistsPort
                .existsByPetIdAndVaccineIdAndApplicationDate(
                        command.petId(),
                        command.vaccineId(),
                        command.applicationDate()
                )) {

            throw new IllegalArgumentException(
                    "Ya existe una vacunación registrada para la misma mascota, vacuna y fecha"
            );
        }

        return saveVaccinationPort.save(vaccination);
    }
}