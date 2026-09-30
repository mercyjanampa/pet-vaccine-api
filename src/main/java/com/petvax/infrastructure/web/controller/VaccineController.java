package com.petvax.infrastructure.web.controller;

import com.petvax.application.command.CreateVaccineCommand;
import com.petvax.application.port.in.CreateVaccineUseCase;
import com.petvax.domain.model.Vaccine;
import com.petvax.infrastructure.web.request.CreateVaccineRequest;
import com.petvax.infrastructure.web.response.VaccineResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.petvax.application.port.in.ListVaccinesUseCase;
import java.util.List;

@RestController
@RequestMapping("/api/vaccines")
public class VaccineController {

    private final CreateVaccineUseCase createVaccineUseCase;
    private final ListVaccinesUseCase listVaccinesUseCase;

    public VaccineController(
            CreateVaccineUseCase createVaccineUseCase,
            ListVaccinesUseCase listVaccinesUseCase
    ) {
        this.createVaccineUseCase = createVaccineUseCase;
        this.listVaccinesUseCase = listVaccinesUseCase;
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
}