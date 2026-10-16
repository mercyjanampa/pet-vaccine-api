package com.petvax.application.service;

import com.petvax.application.exception.ForbiddenOperationException;
import com.petvax.application.port.in.DeletePetUseCase;
import com.petvax.application.port.out.DeletePetPort;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.Role;
import com.petvax.domain.model.User;

public class DeletePetService implements DeletePetUseCase {

    private final LoadPetPort loadPetPort;
    private final LoadUserPort loadUserPort;
    private final DeletePetPort deletePetPort;

    public DeletePetService(
            LoadPetPort loadPetPort,
            LoadUserPort loadUserPort,
            DeletePetPort deletePetPort
    ) {
        this.loadPetPort = loadPetPort;
        this.loadUserPort = loadUserPort;
        this.deletePetPort = deletePetPort;
    }

    @Override
    public void delete(
            Long id,
            String email
    ) {

        Pet pet = loadPetPort.findPetById(id)
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

        // USER solo puede eliminar sus propias mascotas
        if (user.getRole() != Role.ADMIN) {

            if (user.getOwnerId() == null
                    || !user.getOwnerId().equals(pet.getOwnerId())) {

                throw new ForbiddenOperationException(
                        "No tienes acceso para eliminar esta mascota"
                );
            }
        }

        deletePetPort.deletePetById(id);
    }
}