package com.petvax.application.service;

import com.petvax.application.port.in.GetMyPetsUseCase;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.User;

import java.util.List;

public class GetMyPetsService implements GetMyPetsUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadPetPort loadPetPort;

    public GetMyPetsService(
            LoadUserPort loadUserPort,
            LoadPetPort loadPetPort
    ) {
        this.loadUserPort = loadUserPort;
        this.loadPetPort = loadPetPort;
    }

    @Override
    public List<Pet> getMyPets(String email) {

        User user = loadUserPort.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuario no encontrado"
                        )
                );

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