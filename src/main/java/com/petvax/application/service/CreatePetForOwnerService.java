package com.petvax.application.service;

import com.petvax.application.command.CreatePetCommand;
import com.petvax.application.port.in.CreatePetForOwnerUseCase;
import com.petvax.application.port.out.LoadOwnerPort;
import com.petvax.application.port.out.SavePetPort;
import com.petvax.domain.model.Pet;

public class CreatePetForOwnerService
        implements CreatePetForOwnerUseCase {

    private final LoadOwnerPort loadOwnerPort;
    private final SavePetPort savePetPort;

    public CreatePetForOwnerService(
            LoadOwnerPort loadOwnerPort,
            SavePetPort savePetPort
    ) {
        this.loadOwnerPort = loadOwnerPort;
        this.savePetPort = savePetPort;
    }

    @Override
    public Pet createForOwner(
            Long ownerId,
            CreatePetCommand command
    ) {

        loadOwnerPort.findOwnerById(ownerId)
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
                ownerId
        );

        return savePetPort.savePet(pet);
    }
}