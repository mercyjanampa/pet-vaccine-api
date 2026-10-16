package com.petvax.application.service;

import com.petvax.application.command.RegisterVaccinationCommand;
import com.petvax.application.port.in.RegisterVaccinationUseCase;
import com.petvax.application.port.out.CheckVaccinationExistsPort;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.application.port.out.LoadVaccinePort;
import com.petvax.application.port.out.SaveVaccinationPort;
import com.petvax.domain.model.*;
import com.petvax.application.exception.ForbiddenOperationException;

public class RegisterVaccinationService
        implements RegisterVaccinationUseCase {

    private final LoadPetPort loadPetPort;
    private final LoadVaccinePort loadVaccinePort;
    private final LoadUserPort loadUserPort;
    private final SaveVaccinationPort saveVaccinationPort;
    private final CheckVaccinationExistsPort checkVaccinationExistsPort;

    public RegisterVaccinationService(
            LoadPetPort loadPetPort,
            LoadVaccinePort loadVaccinePort,
            LoadUserPort loadUserPort,
            SaveVaccinationPort saveVaccinationPort,
            CheckVaccinationExistsPort checkVaccinationExistsPort
    ) {
        this.loadPetPort = loadPetPort;
        this.loadVaccinePort = loadVaccinePort;
        this.loadUserPort = loadUserPort;
        this.saveVaccinationPort = saveVaccinationPort;
        this.checkVaccinationExistsPort = checkVaccinationExistsPort;
    }

    @Override
    public Vaccination register(
            RegisterVaccinationCommand command,
            String email
    ) {

        // Verifico que la mascota exista
        Pet pet = loadPetPort.findPetById(command.petId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La mascota no existe"
                        )
                );

        // Identifico al usuario autenticado
        User user = loadUserPort.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuario no encontrado"
                        )
                );

        // USER solo puede registrar vacunaciones de sus mascotas
        if (user.getRole() != Role.ADMIN) {

            if (user.getOwnerId() == null
                    || !user.getOwnerId().equals(pet.getOwnerId())) {

                throw new ForbiddenOperationException(
                        "No tienes acceso para registrar vacunaciones en esta mascota"
                );
            }
        }

        // Si pertenece al catálogo, verificamos la vacuna
        if (command.source() == VaccinationSource.CATALOG) {

            Vaccine vaccine = loadVaccinePort
                    .findVaccineById(command.vaccineId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "La vacuna no existe"
                            )
                    );

            // La vacuna debe corresponder a la especie
            if (pet.getSpecies() != vaccine.getSpecies()) {
                throw new IllegalArgumentException(
                        "La vacuna no corresponde a la especie de la mascota"
                );
            }

            // Evitamos duplicados del catálogo
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
        }

        return saveVaccinationPort.save(
                new Vaccination(
                        null,
                        command.petId(),
                        command.vaccineId(),
                        command.customVaccineName(),
                        command.source(),
                        command.applicationDate(),
                        command.nextDoseDate(),
                        command.notes()
                )
        );
    }
}