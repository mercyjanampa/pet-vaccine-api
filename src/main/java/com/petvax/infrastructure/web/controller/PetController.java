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
import org.springframework.security.core.Authentication;
import com.petvax.application.port.in.GetMyPetsUseCase;
import com.petvax.application.port.in.GetRecommendedVaccinesUseCase;
import com.petvax.infrastructure.web.response.VaccineResponse;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final CreatePetUseCase createPetUseCase;
    private final ListPetsUseCase listPetsUseCase;
    private final GetPetUseCase getPetUseCase;
    private final UpdatePetUseCase updatePetUseCase;
    private final DeletePetUseCase deletePetUseCase;
    private final GetMyPetsUseCase getMyPetsUseCase;
    private final GetRecommendedVaccinesUseCase getRecommendedVaccinesUseCase;


    public PetController(
            CreatePetUseCase createPetUseCase,
            ListPetsUseCase listPetsUseCase,
            GetPetUseCase getPetUseCase,
            UpdatePetUseCase updatePetUseCase,
            DeletePetUseCase deletePetUseCase,
            GetMyPetsUseCase getMyPetsUseCase,
            GetRecommendedVaccinesUseCase getRecommendedVaccinesUseCase
    ) {
        this.createPetUseCase = createPetUseCase;
        this.listPetsUseCase = listPetsUseCase;
        this.getPetUseCase = getPetUseCase;
        this.updatePetUseCase = updatePetUseCase;
        this.deletePetUseCase = deletePetUseCase;
        this.getMyPetsUseCase = getMyPetsUseCase;
        this.getRecommendedVaccinesUseCase =
                getRecommendedVaccinesUseCase;
    }

    @PostMapping
    public ResponseEntity<PetResponse> createPet(
            @Valid @RequestBody CreatePetRequest request,
            Authentication authentication
    ) {

        CreatePetCommand command = new CreatePetCommand(
                request.name(),
                request.species(),
                request.breed(),
                request.birthDate(),
                request.sex()
        );

        Pet pet = createPetUseCase.create(
                command,
                authentication.getName()
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

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping
    public ResponseEntity<List<PetResponse>> findAllPets(
            Authentication authentication
    ) {

        List<PetResponse> pets = listPetsUseCase
                .findAll(authentication.getName())
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
            @PathVariable Long id,
            Authentication authentication
    ) {

        Pet pet = getPetUseCase.findById(
                id,
                authentication.getName()
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
    @GetMapping("/my")
    public ResponseEntity<List<PetResponse>> getMyPets(
            Authentication authentication
    ) {

        List<PetResponse> pets = getMyPetsUseCase
                .getMyPets(authentication.getName())
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
    @GetMapping("/{petId}/recommended-vaccines")
    public ResponseEntity<List<VaccineResponse>> getRecommendedVaccines(
            @PathVariable Long petId,
            Authentication authentication
    ) {

        List<VaccineResponse> vaccines =
                getRecommendedVaccinesUseCase
                        .getRecommendedVaccines(
                                petId,
                                authentication.getName()
                        )
                        .stream()
                        .map(vaccine ->
                                new VaccineResponse(
                                        vaccine.getId(),
                                        vaccine.getName(),
                                        vaccine.getSpecies(),
                                        vaccine.getDescription(),
                                        vaccine.getRecommendedIntervalMonths()
                                )
                        )
                        .toList();

        return ResponseEntity.ok(vaccines);
    }
}