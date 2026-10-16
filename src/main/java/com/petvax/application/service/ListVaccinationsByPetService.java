package com.petvax.application.service;

import com.petvax.application.exception.ForbiddenOperationException;
import com.petvax.application.port.in.ListVaccinationsByPetUseCase;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.application.port.out.LoadVaccinationsPort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.Role;
import com.petvax.domain.model.User;
import com.petvax.domain.model.Vaccination;

import java.util.List;

public class ListVaccinationsByPetService
        implements ListVaccinationsByPetUseCase {

    private final LoadPetPort loadPetPort;
    private final LoadUserPort loadUserPort;
    private final LoadVaccinationsPort loadVaccinationsPort;

    public ListVaccinationsByPetService(
            LoadPetPort loadPetPort,
            LoadUserPort loadUserPort,
            LoadVaccinationsPort loadVaccinationsPort
    ) {
        this.loadPetPort = loadPetPort;
        this.loadUserPort = loadUserPort;
        this.loadVaccinationsPort = loadVaccinationsPort;
    }

    @Override
    public List<Vaccination> findByPetId(
            Long petId,
            String email
    ) {

        Pet pet = loadPetPort.findPetById(petId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La mascota no existe"
                        )
                );

        User user = loadUserPort.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuario no encontrado"
                        )
                );

        // ADMIN puede consultar las vacunaciones de cualquier mascota
        if (user.getRole() != Role.ADMIN) {

            // USER solo puede consultar las vacunaciones de sus mascotas
            if (user.getOwnerId() == null
                    || !user.getOwnerId().equals(pet.getOwnerId())) {

                throw new ForbiddenOperationException(
                        "No tienes acceso a las vacunaciones de esta mascota"
                );
            }
        }

        return loadVaccinationsPort
                .findVaccinationsByPetId(petId);
    }
}