package com.petvax.infrastructure.web.controller;

import com.petvax.application.command.CreateOwnerCommand;
import com.petvax.application.port.in.CreateOwnerUseCase;
import com.petvax.application.port.in.ListOwnersUseCase;
import com.petvax.domain.model.Owner;
import com.petvax.infrastructure.web.request.CreateOwnerRequest;
import com.petvax.infrastructure.web.response.OwnerResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.petvax.application.port.in.GetOwnerUseCase;
import com.petvax.application.port.in.UpdateOwnerUseCase;
import com.petvax.application.port.in.DeleteOwnerUseCase;
import com.petvax.infrastructure.web.request.UpdateOwnerRequest;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final CreateOwnerUseCase createOwnerUseCase;
    private final ListOwnersUseCase listOwnersUseCase;
    private final GetOwnerUseCase getOwnerUseCase;
    private final UpdateOwnerUseCase updateOwnerUseCase;
    private final DeleteOwnerUseCase deleteOwnerUseCase;

    public OwnerController(
            CreateOwnerUseCase createOwnerUseCase,
            ListOwnersUseCase listOwnersUseCase,
            GetOwnerUseCase getOwnerUseCase,
            UpdateOwnerUseCase updateOwnerUseCase,
            DeleteOwnerUseCase deleteOwnerUseCase
    ) {
        this.createOwnerUseCase = createOwnerUseCase;
        this.listOwnersUseCase = listOwnersUseCase;
        this.getOwnerUseCase = getOwnerUseCase;
        this.updateOwnerUseCase = updateOwnerUseCase;
        this.deleteOwnerUseCase = deleteOwnerUseCase;
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

    @GetMapping
    public ResponseEntity<List<OwnerResponse>> findAllOwners() {

        List<OwnerResponse> owners = listOwnersUseCase.findAll()
                .stream()
                .map(owner -> new OwnerResponse(
                        owner.getId(),
                        owner.getName(),
                        owner.getEmail(),
                        owner.getPhone()
                ))
                .toList();

        return ResponseEntity.ok(owners);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponse> findOwnerById(
            @PathVariable Long id
    ) {

        Owner owner = getOwnerUseCase.findById(id);

        OwnerResponse response = new OwnerResponse(
                owner.getId(),
                owner.getName(),
                owner.getEmail(),
                owner.getPhone()
        );

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<OwnerResponse> updateOwner(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOwnerRequest request
    ) {

        Owner owner = updateOwnerUseCase.update(
                id,
                request.name(),
                request.email(),
                request.phone()
        );

        OwnerResponse response = new OwnerResponse(
                owner.getId(),
                owner.getName(),
                owner.getEmail(),
                owner.getPhone()
        );

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(
            @PathVariable Long id
    ) {

        deleteOwnerUseCase.delete(id);

        return ResponseEntity.noContent().build();
    }
}