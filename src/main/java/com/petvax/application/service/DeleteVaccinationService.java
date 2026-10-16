package com.petvax.application.service;

import com.petvax.application.exception.ForbiddenOperationException;
import com.petvax.application.port.in.DeleteVaccinationUseCase;
import com.petvax.application.port.out.DeleteVaccinationPort;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.application.port.out.LoadVaccinationsPort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.Role;
import com.petvax.domain.model.User;
import com.petvax.domain.model.Vaccination;

public class DeleteVaccinationService
        implements DeleteVaccinationUseCase {

    private final LoadVaccinationsPort loadVaccinationsPort;
    private final LoadPetPort loadPetPort;
    private final LoadUserPort loadUserPort;
    private final DeleteVaccinationPort deleteVaccinationPort;

    public DeleteVaccinationService(
            LoadVaccinationsPort loadVaccinationsPort,
            LoadPetPort loadPetPort,
            LoadUserPort loadUserPort,
            DeleteVaccinationPort deleteVaccinationPort
    ) {
        this.loadVaccinationsPort = loadVaccinationsPort;
        this.loadPetPort = loadPetPort;
        this.loadUserPort = loadUserPort;
        this.deleteVaccinationPort = deleteVaccinationPort;
    }

    @Override
    public void delete(
            Long id,
            String email
    ) {

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

        Pet pet = loadPetPort
                .findPetById(vaccination.getPetId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La mascota no existe"
                        )
                );

        // USER solo puede eliminar vacunaciones de sus mascotas
        if (user.getRole() != Role.ADMIN) {

            if (user.getOwnerId() == null
                    || !user.getOwnerId().equals(pet.getOwnerId())) {

                throw new ForbiddenOperationException(
                        "No tienes acceso para eliminar esta vacunación"
                );
            }
        }

        deleteVaccinationPort.deleteVaccinationById(id);
    }
}