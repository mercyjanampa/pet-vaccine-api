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

@RestController
@RequestMapping("/api/vaccines")
public class VaccineController {

    private final CreateVaccineUseCase createVaccineUseCase;

    public VaccineController(CreateVaccineUseCase createVaccineUseCase) {
        this.createVaccineUseCase = createVaccineUseCase;
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
}