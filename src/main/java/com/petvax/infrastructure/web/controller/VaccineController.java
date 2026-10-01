package com.petvax.infrastructure.web.controller;

import com.petvax.application.command.CreateVaccineCommand;
import com.petvax.application.port.in.*;
import com.petvax.domain.model.Vaccine;
import com.petvax.infrastructure.web.request.CreateVaccineRequest;
import com.petvax.infrastructure.web.response.VaccineResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.petvax.infrastructure.web.request.UpdateVaccineRequest;
import com.petvax.domain.model.Species;

@RestController
@RequestMapping("/api/vaccines")
public class VaccineController {

    private final CreateVaccineUseCase createVaccineUseCase;
    private final ListVaccinesUseCase listVaccinesUseCase;
    private final GetVaccineUseCase getVaccineUseCase;
    private final UpdateVaccineUseCase updateVaccineUseCase;
    private final DeleteVaccineUseCase deleteVaccineUseCase;
    private final ListVaccinesBySpeciesUseCase listVaccinesBySpeciesUseCase;

    public VaccineController(
            CreateVaccineUseCase createVaccineUseCase,
            ListVaccinesUseCase listVaccinesUseCase,
            GetVaccineUseCase getVaccineUseCase,
            UpdateVaccineUseCase updateVaccineUseCase,
            DeleteVaccineUseCase deleteVaccineUseCase,
            ListVaccinesBySpeciesUseCase listVaccinesBySpeciesUseCase
    ) {
        this.createVaccineUseCase = createVaccineUseCase;
        this.listVaccinesUseCase = listVaccinesUseCase;
        this.getVaccineUseCase = getVaccineUseCase;
        this.updateVaccineUseCase = updateVaccineUseCase;
        this.deleteVaccineUseCase = deleteVaccineUseCase;
        this.listVaccinesBySpeciesUseCase = listVaccinesBySpeciesUseCase;
    }

    @PostMapping
    public ResponseEntity<VaccineResponse> createVaccine(
            @Valid @RequestBody CreateVaccineRequest request
    ) {

        CreateVaccineCommand command = new CreateVaccineCommand(
                request.name(),
                request.species(),
                request.description(),
                request.recommendedIntervalMonths()
        );

        Vaccine vaccine = createVaccineUseCase.create(command);

        VaccineResponse response = new VaccineResponse(
                vaccine.getId(),
                vaccine.getName(),
                vaccine.getSpecies(),
                vaccine.getDescription(),
                vaccine.getRecommendedIntervalMonths()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping
    public ResponseEntity<List<VaccineResponse>> findAllVaccines() {

        List<VaccineResponse> vaccines = listVaccinesUseCase.findAll()
                .stream()
                .map(vaccine -> new VaccineResponse(
                        vaccine.getId(),
                        vaccine.getName(),
                        vaccine.getSpecies(),
                        vaccine.getDescription(),
                        vaccine.getRecommendedIntervalMonths()
                ))
                .toList();

        return ResponseEntity.ok(vaccines);
    }
    @GetMapping("/{id}")
    public ResponseEntity<VaccineResponse> findVaccineById(
            @PathVariable Long id
    ) {

        Vaccine vaccine = getVaccineUseCase.findById(id);

        VaccineResponse response = new VaccineResponse(
                vaccine.getId(),
                vaccine.getName(),
                vaccine.getSpecies(),
                vaccine.getDescription(),
                vaccine.getRecommendedIntervalMonths()
        );

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<VaccineResponse> updateVaccine(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVaccineRequest request
    ) {

        Vaccine vaccine = updateVaccineUseCase.update(
                id,
                request.name(),
                request.species(),
                request.description(),
                request.recommendedIntervalMonths()
        );

        VaccineResponse response = new VaccineResponse(
                vaccine.getId(),
                vaccine.getName(),
                vaccine.getSpecies(),
                vaccine.getDescription(),
                vaccine.getRecommendedIntervalMonths()
        );

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaccine(
            @PathVariable Long id
    ) {

        deleteVaccineUseCase.delete(id);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/species/{species}")
    public ResponseEntity<List<VaccineResponse>> findVaccinesBySpecies(
            @PathVariable Species species
    ) {

        List<VaccineResponse> vaccines =
                listVaccinesBySpeciesUseCase.findBySpecies(species)
                        .stream()
                        .map(vaccine -> new VaccineResponse(
                                vaccine.getId(),
                                vaccine.getName(),
                                vaccine.getSpecies(),
                                vaccine.getDescription(),
                                vaccine.getRecommendedIntervalMonths()
                        ))
                        .toList();

        return ResponseEntity.ok(vaccines);
    }
}