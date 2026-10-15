package com.petvax.application.service;

import com.petvax.application.port.in.ListPetsUseCase;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.Role;
import com.petvax.domain.model.User;

import java.util.List;

public class ListPetsService implements ListPetsUseCase {

    private final LoadPetPort loadPetPort;
    private final LoadUserPort loadUserPort;

    public ListPetsService(
            LoadPetPort loadPetPort,
            LoadUserPort loadUserPort
    ) {
        this.loadPetPort = loadPetPort;
        this.loadUserPort = loadUserPort;
    }

    @Override
    public List<Pet> findAll(String email) {

        User user = loadUserPort.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuario no encontrado"
                        )
                );

        if (user.getRole() == Role.ADMIN) {
            return loadPetPort.findAllPets();
        }

        if (user.getOwnerId() == null) {
            throw new IllegalArgumentException(
                    "El usuario no tiene propietario asociado"
            );
        }

        return loadPetPort.findPetsByOwnerId(
                user.getOwnerId()
        );
    }
}