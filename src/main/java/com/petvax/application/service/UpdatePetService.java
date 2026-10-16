package com.petvax.application.service;

import com.petvax.application.exception.ForbiddenOperationException;
import com.petvax.application.port.in.UpdatePetUseCase;
import com.petvax.application.port.out.LoadOwnerPort;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.application.port.out.SavePetPort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.Role;
import com.petvax.domain.model.Species;
import com.petvax.domain.model.User;

import java.time.LocalDate;

public class UpdatePetService implements UpdatePetUseCase {

    private final LoadPetPort loadPetPort;
    private final LoadOwnerPort loadOwnerPort;
    private final LoadUserPort loadUserPort;
    private final SavePetPort savePetPort;

    public UpdatePetService(
            LoadPetPort loadPetPort,
            LoadOwnerPort loadOwnerPort,
            LoadUserPort loadUserPort,
            SavePetPort savePetPort
    ) {
        this.loadPetPort = loadPetPort;
        this.loadOwnerPort = loadOwnerPort;
        this.loadUserPort = loadUserPort;
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
            Long ownerId,
            String email
    ) {

        Pet existingPet = loadPetPort.findPetById(id)
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

        // USER solo puede actualizar sus propias mascotas
        if (user.getRole() != Role.ADMIN) {

            if (user.getOwnerId() == null
                    || !user.getOwnerId().equals(existingPet.getOwnerId())) {

                throw new ForbiddenOperationException(
                        "No tienes acceso para actualizar esta mascota"
                );
            }

            // Un USER no puede cambiar el propietario de su mascota
            if (!existingPet.getOwnerId().equals(ownerId)) {

                throw new ForbiddenOperationException(
                        "No puedes cambiar el propietario de la mascota"
                );
            }
        }

        loadOwnerPort.findOwnerById(ownerId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "El dueño no existe"
                        )
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