package com.petvax.infrastructure.web.controller;

import com.petvax.application.command.CreatePetCommand;
import com.petvax.application.port.in.CreatePetUseCase;
import com.petvax.domain.model.Pet;
import com.petvax.infrastructure.web.request.CreatePetRequest;
import com.petvax.infrastructure.web.response.PetResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.petvax.application.port.in.ListPetsUseCase;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final CreatePetUseCase createPetUseCase;
    private final ListPetsUseCase listPetsUseCase;

    public PetController(
            CreatePetUseCase createPetUseCase,
            ListPetsUseCase listPetsUseCase
    ) {
        this.createPetUseCase = createPetUseCase;
        this.listPetsUseCase = listPetsUseCase;
    }

    @PostMapping
    public ResponseEntity<PetResponse> createPet(
            @Valid @RequestBody CreatePetRequest request
    ) {

        CreatePetCommand command = new CreatePetCommand(
                request.name(),
                request.species(),
                request.breed(),
                request.birthDate(),
                request.sex(),
                request.ownerId()
        );

        Pet pet = createPetUseCase.create(command);

        PetResponse response = new PetResponse(
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
    @GetMapping
    public ResponseEntity<List<PetResponse>> findAllPets() {

        List<PetResponse> pets = listPetsUseCase.findAll()
                .stream()
                .map(pet -> new PetResponse(
                        pet.getId(),
                        pet.getName(),
                        pet.getSpecies(),
                        pet.getBreed(),
                        pet.getBirthDate(),
                        pet.getSex(),
                        pet.getOwnerId()
                ))
                .toList();

        return ResponseEntity.ok(pets);
    }
}