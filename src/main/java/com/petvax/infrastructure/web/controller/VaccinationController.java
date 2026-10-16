package com.petvax.infrastructure.web.controller;

import com.petvax.application.command.RegisterVaccinationCommand;
import com.petvax.application.port.in.DeleteVaccinationUseCase;
import com.petvax.application.port.in.GetVaccinationUseCase;
import com.petvax.application.port.in.ListExpiredVaccinationsUseCase;
import com.petvax.application.port.in.ListVaccinationsByPetUseCase;
import com.petvax.application.port.in.ListVaccinationsUseCase;
import com.petvax.application.port.in.RegisterVaccinationUseCase;
import com.petvax.application.port.in.UpdateVaccinationUseCase;
import com.petvax.domain.model.Vaccination;
import com.petvax.infrastructure.web.request.RegisterVaccinationRequest;
import com.petvax.infrastructure.web.request.UpdateVaccinationRequest;
import com.petvax.infrastructure.web.response.VaccinationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.petvax.application.port.in.GetMyVaccinationsUseCase;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/vaccinations")
public class VaccinationController {

    private final RegisterVaccinationUseCase registerVaccinationUseCase;
    private final ListVaccinationsUseCase listVaccinationsUseCase;
    private final GetVaccinationUseCase getVaccinationUseCase;
    private final ListVaccinationsByPetUseCase listVaccinationsByPetUseCase;
    private final ListExpiredVaccinationsUseCase listExpiredVaccinationsUseCase;
    private final UpdateVaccinationUseCase updateVaccinationUseCase;
    private final DeleteVaccinationUseCase deleteVaccinationUseCase;
    private final GetMyVaccinationsUseCase getMyVaccinationsUseCase;

    public VaccinationController(
            RegisterVaccinationUseCase registerVaccinationUseCase,
            ListVaccinationsUseCase listVaccinationsUseCase,
            GetVaccinationUseCase getVaccinationUseCase,
            ListVaccinationsByPetUseCase listVaccinationsByPetUseCase,
            ListExpiredVaccinationsUseCase listExpiredVaccinationsUseCase,
            UpdateVaccinationUseCase updateVaccinationUseCase,
            DeleteVaccinationUseCase deleteVaccinationUseCase,
            GetMyVaccinationsUseCase getMyVaccinationsUseCase
    ) {
        this.registerVaccinationUseCase = registerVaccinationUseCase;
        this.listVaccinationsUseCase = listVaccinationsUseCase;
        this.getVaccinationUseCase = getVaccinationUseCase;
        this.listVaccinationsByPetUseCase = listVaccinationsByPetUseCase;
        this.listExpiredVaccinationsUseCase = listExpiredVaccinationsUseCase;
        this.updateVaccinationUseCase = updateVaccinationUseCase;
        this.deleteVaccinationUseCase = deleteVaccinationUseCase;
        this.getMyVaccinationsUseCase = getMyVaccinationsUseCase;
    }

    @PostMapping
    public ResponseEntity<VaccinationResponse> register(
            @Valid @RequestBody RegisterVaccinationRequest request,
            Authentication authentication
    ) {

        RegisterVaccinationCommand command =
                new RegisterVaccinationCommand(
                        request.petId(),
                        request.vaccineId(),
                        request.customVaccineName(),
                        request.source(),
                        request.applicationDate(),
                        request.nextDoseDate(),
                        request.notes()
                );

        Vaccination vaccination =
                registerVaccinationUseCase.register(
                        command,
                        authentication.getName()
                );

        VaccinationResponse response =
                toResponse(vaccination);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<VaccinationResponse>> findAllVaccinations(
            Authentication authentication
    ) {

        List<VaccinationResponse> vaccinations =
                listVaccinationsUseCase.findAll(
                                authentication.getName()
                        )
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ResponseEntity.ok(vaccinations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaccinationResponse> findVaccinationById(
            @PathVariable Long id,
            Authentication authentication
    ) {

        Vaccination vaccination =
                getVaccinationUseCase.findById(
                        id,
                        authentication.getName()
                );

        return ResponseEntity.ok(
                toResponse(vaccination)
        );
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<VaccinationResponse>> findByPet(
            @PathVariable Long petId
    ) {

        List<VaccinationResponse> vaccinations =
                listVaccinationsByPetUseCase.findByPetId(petId)
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ResponseEntity.ok(vaccinations);
    }

    @GetMapping("/expired")
    public ResponseEntity<List<VaccinationResponse>> findExpiredVaccinations() {

        List<VaccinationResponse> vaccinations =
                listExpiredVaccinationsUseCase.findExpired()
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ResponseEntity.ok(vaccinations);
    }

    @GetMapping("/my")
    public ResponseEntity<List<VaccinationResponse>> getMyVaccinations(
            Authentication authentication
    ) {

        List<VaccinationResponse> vaccinations =
                getMyVaccinationsUseCase
                        .getMyVaccinations(authentication.getName())
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ResponseEntity.ok(vaccinations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VaccinationResponse> updateVaccination(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVaccinationRequest request
    ) {

        Vaccination vaccination =
                updateVaccinationUseCase.update(
                        id,
                        request.petId(),
                        request.vaccineId(),
                        request.applicationDate(),
                        request.nextDoseDate(),
                        request.notes()
                );

        return ResponseEntity.ok(
                toResponse(vaccination)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaccination(
            @PathVariable Long id
    ) {

        deleteVaccinationUseCase.delete(id);

        return ResponseEntity.noContent().build();
    }

    private VaccinationResponse toResponse(
            Vaccination vaccination
    ) {

        return new VaccinationResponse(
                vaccination.getId(),
                vaccination.getPetId(),
                vaccination.getVaccineId(),
                vaccination.getCustomVaccineName(),
                vaccination.getSource(),
                vaccination.getApplicationDate(),
                vaccination.getNextDoseDate(),
                vaccination.getNotes(),
                vaccination.getStatus(),
                vaccination.getRecommendedAction()
        );
    }
}