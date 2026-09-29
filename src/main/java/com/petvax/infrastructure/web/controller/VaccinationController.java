package com.petvax.infrastructure.web.controller;

import com.petvax.application.command.RegisterVaccinationCommand;
import com.petvax.application.port.in.RegisterVaccinationUseCase;
import com.petvax.domain.model.Vaccination;
import com.petvax.infrastructure.web.request.RegisterVaccinationRequest;
import com.petvax.infrastructure.web.response.VaccinationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vaccinations")
public class VaccinationController {

    private final RegisterVaccinationUseCase registerVaccinationUseCase;

    public VaccinationController(
            RegisterVaccinationUseCase registerVaccinationUseCase
    ) {
        this.registerVaccinationUseCase = registerVaccinationUseCase;
    }

    @PostMapping
    public ResponseEntity<VaccinationResponse> register(
            @Valid @RequestBody RegisterVaccinationRequest request
    ) {

        RegisterVaccinationCommand command =
                new RegisterVaccinationCommand(
                        request.petId(),
                        request.vaccineId(),
                        request.applicationDate(),
                        request.nextDoseDate(),
                        request.notes()
                );

        Vaccination vaccination =
                registerVaccinationUseCase.register(command);

        VaccinationResponse response =
                new VaccinationResponse(
                        vaccination.getId(),
                        vaccination.getPetId(),
                        vaccination.getVaccineId(),
                        vaccination.getApplicationDate(),
                        vaccination.getNextDoseDate(),
                        vaccination.getNotes(),
                        vaccination.getStatus(),
                        vaccination.getRecommendedAction()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}