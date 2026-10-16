package com.petvax.infrastructure.web.controller;

import com.petvax.application.command.CreatePetCommand;
import com.petvax.application.port.in.CreatePetForOwnerUseCase;
import com.petvax.domain.model.Pet;
import com.petvax.infrastructure.web.request.CreatePetRequest;
import com.petvax.infrastructure.web.response.PetResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/owners")
public class AdminPetController {

    private final CreatePetForOwnerUseCase createPetForOwnerUseCase;

    public AdminPetController(
            CreatePetForOwnerUseCase createPetForOwnerUseCase
    ) {
        this.createPetForOwnerUseCase =
                createPetForOwnerUseCase;
    }

    @PostMapping("/{ownerId}/pets")
    public ResponseEntity<PetResponse> createPetForOwner(
            @PathVariable Long ownerId,
            @Valid @RequestBody CreatePetRequest request
    ) {

        CreatePetCommand command =
                new CreatePetCommand(
                        request.name(),
                        request.species(),
                        request.breed(),
                        request.birthDate(),
                        request.sex()
                );

        Pet pet =
                createPetForOwnerUseCase.createForOwner(
                        ownerId,
                        command
                );

        PetResponse response =
                new PetResponse(
                        pet.getId(),
                        pet.getName(),
                        pet.getSpecies(),
                        pet.getBreed(),
                        pet.getBirthDate(),
                        pet.getSex(),
                        pet.getOwnerId()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}