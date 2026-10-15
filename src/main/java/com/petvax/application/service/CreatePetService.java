package com.petvax.application.service;

import com.petvax.application.command.CreatePetCommand;
import com.petvax.application.port.in.CreatePetUseCase;
import com.petvax.application.port.out.LoadOwnerPort;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.application.port.out.SavePetPort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.User;

public class CreatePetService implements CreatePetUseCase {

    private final LoadOwnerPort loadOwnerPort;
    private final LoadUserPort loadUserPort;
    private final SavePetPort savePetPort;

    public CreatePetService(
            LoadOwnerPort loadOwnerPort,
            LoadUserPort loadUserPort,
            SavePetPort savePetPort
    ) {
        this.loadOwnerPort = loadOwnerPort;
        this.loadUserPort = loadUserPort;
        this.savePetPort = savePetPort;
    }

    @Override
    public Pet create(
            CreatePetCommand command,
            String email
    ) {

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

        loadOwnerPort.findOwnerById(user.getOwnerId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "El dueño no existe"
                        )
                );

        Pet pet = new Pet(
                null,
                command.name(),
                command.species(),
                command.breed(),
                command.birthDate(),
                command.sex(),
                user.getOwnerId()
        );

        return savePetPort.savePet(pet);
    }
}