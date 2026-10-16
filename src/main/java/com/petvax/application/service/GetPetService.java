package com.petvax.application.service;

import com.petvax.application.port.in.GetPetUseCase;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.Role;
import com.petvax.domain.model.User;
import com.petvax.application.exception.ForbiddenOperationException;

public class GetPetService implements GetPetUseCase {

    private final LoadPetPort loadPetPort;
    private final LoadUserPort loadUserPort;

    public GetPetService(
            LoadPetPort loadPetPort,
            LoadUserPort loadUserPort
    ) {
        this.loadPetPort = loadPetPort;
        this.loadUserPort = loadUserPort;
    }

    @Override
    public Pet findById(Long id, String email) {

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

        // El administrador puede consultar cualquier mascota
        if (user.getRole() == Role.ADMIN) {
            return pet;
        }

        // El usuario solo puede consultar sus propias mascotas
        if (user.getOwnerId() == null
                || !user.getOwnerId().equals(pet.getOwnerId())) {

            throw new ForbiddenOperationException(
                    "No tienes acceso a esta mascota"
            );
        }

        return pet;
    }
}