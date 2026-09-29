package com.petvax.application.service;

import com.petvax.application.command.CreatePetCommand;
import com.petvax.application.port.in.CreatePetUseCase;
import com.petvax.application.port.out.LoadOwnerPort;
import com.petvax.application.port.out.SavePetPort;
import com.petvax.domain.model.Pet;

public class CreatePetService implements CreatePetUseCase {

    private final LoadOwnerPort loadOwnerPort;
    private final SavePetPort savePetPort;

    public CreatePetService(
            LoadOwnerPort loadOwnerPort,
            SavePetPort savePetPort
    ) {
        this.loadOwnerPort = loadOwnerPort;
        this.savePetPort = savePetPort;
    }

    @Override
    public Pet create(CreatePetCommand command) {

        loadOwnerPort.findOwnerById(command.ownerId())
                .orElseThrow(() ->
                        new IllegalArgumentException("El dueño no existe")
                );

        Pet pet = new Pet(
                null,
                command.name(),
                command.species(),
                command.breed(),
                command.birthDate(),
                command.sex(),
                command.ownerId()
        );

        return savePetPort.savePet(pet);
    }
}