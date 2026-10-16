package com.petvax.application.service;

import com.petvax.application.port.in.GetVaccinationUseCase;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.application.port.out.LoadVaccinationsPort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.Role;
import com.petvax.domain.model.User;
import com.petvax.domain.model.Vaccination;
import com.petvax.application.exception.ForbiddenOperationException;

public class GetVaccinationService
        implements GetVaccinationUseCase {

    private final LoadVaccinationsPort loadVaccinationsPort;
    private final LoadPetPort loadPetPort;
    private final LoadUserPort loadUserPort;

    public GetVaccinationService(
            LoadVaccinationsPort loadVaccinationsPort,
            LoadPetPort loadPetPort,
            LoadUserPort loadUserPort
    ) {
        this.loadVaccinationsPort = loadVaccinationsPort;
        this.loadPetPort = loadPetPort;
        this.loadUserPort = loadUserPort;
    }

    @Override
    public Vaccination findById(Long id, String email) {

        Vaccination vaccination =
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

        // El administrador puede consultar cualquier vacunación
        if (user.getRole() == Role.ADMIN) {
            return vaccination;
        }

        Pet pet = loadPetPort
                .findPetById(vaccination.getPetId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La mascota no existe"
                        )
                );

        if (user.getOwnerId() == null
                || !user.getOwnerId().equals(pet.getOwnerId())) {

            throw new ForbiddenOperationException(
                    "No tienes acceso a esta vacunación"
            );
        }

        return vaccination;
    }
}