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
import com.petvax.application.port.in.GetPetUseCase;
import com.petvax.application.port.in.UpdatePetUseCase;
import com.petvax.application.port.in.DeletePetUseCase;
import com.petvax.infrastructure.web.request.UpdatePetRequest;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final CreatePetUseCase createPetUseCase;
    private final ListPetsUseCase listPetsUseCase;
    private final GetPetUseCase getPetUseCase;
    private final UpdatePetUseCase updatePetUseCase;
    private final DeletePetUseCase deletePetUseCase;

    public PetController(
            CreatePetUseCase createPetUseCase,
            ListPetsUseCase listPetsUseCase,
            GetPetUseCase getPetUseCase,
            UpdatePetUseCase updatePetUseCase,
            DeletePetUseCase deletePetUseCase
    ) {
        this.createPetUseCase = createPetUseCase;
        this.listPetsUseCase = listPetsUseCase;
        this.getPetUseCase = getPetUseCase;
        this.updatePetUseCase = updatePetUseCase;
        this.deletePetUseCase = deletePetUseCase;
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
    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> findPetById(
            @PathVariable Long id
    ) {

        Pet pet = getPetUseCase.findById(id);

        PetResponse response = new PetResponse(
                pet.getId(),
                pet.getName(),
                pet.getSpecies(),
                pet.getBreed(),
                pet.getBirthDate(),
                pet.getSex(),
                pet.getOwnerId()
        );

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PetResponse> updatePet(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePetRequest request
    ) {

        Pet pet = updatePetUseCase.update(
                id,
                request.name(),
                request.species(),
                request.breed(),
                request.birthDate(),
                request.sex(),
                request.ownerId()
        );

        PetResponse response = new PetResponse(
                pet.getId(),
                pet.getName(),
                pet.getSpecies(),
                pet.getBreed(),
                pet.getBirthDate(),
                pet.getSex(),
                pet.getOwnerId()
        );

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(
            @PathVariable Long id
    ) {

        deletePetUseCase.delete(id);

        return ResponseEntity.noContent().build();
    }
}