package com.petvax.infrastructure.web.controller;

import com.petvax.application.command.CreateOwnerCommand;
import com.petvax.application.port.in.CreateOwnerUseCase;
import com.petvax.domain.model.Owner;
import com.petvax.infrastructure.web.request.CreateOwnerRequest;
import com.petvax.infrastructure.web.response.OwnerResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final CreateOwnerUseCase createOwnerUseCase;

    public OwnerController(CreateOwnerUseCase createOwnerUseCase) {
        this.createOwnerUseCase = createOwnerUseCase;
    }

    @PostMapping
    public ResponseEntity<OwnerResponse> createOwner(
            @Valid @RequestBody CreateOwnerRequest request
    ) {

        CreateOwnerCommand command = new CreateOwnerCommand(
                request.name(),
                request.email(),
                request.phone()
        );

        Owner owner = createOwnerUseCase.create(command);

        OwnerResponse response = new OwnerResponse(
                owner.getId(),
                owner.getName(),
                owner.getEmail(),
                owner.getPhone()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}