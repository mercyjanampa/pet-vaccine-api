package com.petvax.application.service;

import com.petvax.application.port.in.UpdatePetUseCase;
import com.petvax.application.port.out.LoadOwnerPort;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.SavePetPort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.Species;

import java.time.LocalDate;

public class UpdatePetService implements UpdatePetUseCase {

    private final LoadPetPort loadPetPort;
    private final LoadOwnerPort loadOwnerPort;
    private final SavePetPort savePetPort;

    public UpdatePetService(
            LoadPetPort loadPetPort,
            LoadOwnerPort loadOwnerPort,
            SavePetPort savePetPort
    ) {
        this.loadPetPort = loadPetPort;
        this.loadOwnerPort = loadOwnerPort;
        this.savePetPort = savePetPort;
    }

    @Override
    public Pet update(
            Long id,
            String name,
            Species species,
            String breed,
            LocalDate birthDate,
            String sex,
            Long ownerId
    ) {

        loadPetPort.findPetById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("La mascota no existe")
                );

        loadOwnerPort.findOwnerById(ownerId)
                .orElseThrow(() ->
                        new IllegalArgumentException("El dueño no existe")
                );

        Pet updatedPet = new Pet(
                id,
                name,
                species,
                breed,
                birthDate,
                sex,
                ownerId
        );

        return savePetPort.savePet(updatedPet);
    }
}