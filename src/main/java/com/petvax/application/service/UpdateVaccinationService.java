package com.petvax.application.service;

import com.petvax.application.exception.ForbiddenOperationException;
import com.petvax.application.port.in.UpdateVaccinationUseCase;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.application.port.out.LoadVaccinePort;
import com.petvax.application.port.out.LoadVaccinationsPort;
import com.petvax.application.port.out.SaveVaccinationPort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.Role;
import com.petvax.domain.model.User;
import com.petvax.domain.model.Vaccination;
import com.petvax.domain.model.Vaccine;

import java.time.LocalDate;

public class UpdateVaccinationService
        implements UpdateVaccinationUseCase {

    private final LoadVaccinationsPort loadVaccinationsPort;
    private final LoadPetPort loadPetPort;
    private final LoadVaccinePort loadVaccinePort;
    private final LoadUserPort loadUserPort;
    private final SaveVaccinationPort saveVaccinationPort;

    public UpdateVaccinationService(
            LoadVaccinationsPort loadVaccinationsPort,
            LoadPetPort loadPetPort,
            LoadVaccinePort loadVaccinePort,
            LoadUserPort loadUserPort,
            SaveVaccinationPort saveVaccinationPort
    ) {
        this.loadVaccinationsPort = loadVaccinationsPort;
        this.loadPetPort = loadPetPort;
        this.loadVaccinePort = loadVaccinePort;
        this.loadUserPort = loadUserPort;
        this.saveVaccinationPort = saveVaccinationPort;
    }

    @Override
    public Vaccination update(
            Long id,
            Long petId,
            Long vaccineId,
            LocalDate applicationDate,
            LocalDate nextDoseDate,
            String notes,
            String email
    ) {

        Vaccination existingVaccination =
                loadVaccinationsPort.findVaccinationById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "La vacunación no existe"
                                )
                        );

        User user = loadUserPort.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuario no encontrado"
                        )
                );

        Pet currentPet = loadPetPort
                .findPetById(existingVaccination.getPetId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La mascota no existe"
                        )
                );

        // USER solo puede actualizar vacunaciones de sus mascotas
        if (user.getRole() != Role.ADMIN) {

            if (user.getOwnerId() == null
                    || !user.getOwnerId().equals(currentPet.getOwnerId())) {

                throw new ForbiddenOperationException(
                        "No tienes acceso para actualizar esta vacunación"
                );
            }
        }

        Pet pet = loadPetPort.findPetById(petId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La mascota no existe"
                        )
                );

        // USER tampoco puede mover la vacunación a una mascota ajena
        if (user.getRole() != Role.ADMIN) {

            if (!user.getOwnerId().equals(pet.getOwnerId())) {

                throw new ForbiddenOperationException(
                        "No puedes asignar la vacunación a otra mascota"
                );
            }
        }

        Vaccine vaccine = loadVaccinePort.findVaccineById(vaccineId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La vacuna no existe"
                        )
                );

        if (pet.getSpecies() != vaccine.getSpecies()) {
            throw new IllegalArgumentException(
                    "La vacuna no corresponde a la especie de la mascota"
            );
        }

        Vaccination vaccination = new Vaccination(
                id,
                petId,
                vaccineId,
                applicationDate,
                nextDoseDate,
                notes
        );

        return saveVaccinationPort.save(vaccination);
    }
}