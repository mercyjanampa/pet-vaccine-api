package com.petvax.application.service;

import com.petvax.application.port.in.UpdateOwnerUseCase;
import com.petvax.application.port.out.LoadOwnerPort;
import com.petvax.application.port.out.SaveOwnerPort;
import com.petvax.domain.model.Owner;

public class UpdateOwnerService implements UpdateOwnerUseCase {

    private final LoadOwnerPort loadOwnerPort;
    private final SaveOwnerPort saveOwnerPort;

    public UpdateOwnerService(
            LoadOwnerPort loadOwnerPort,
            SaveOwnerPort saveOwnerPort
    ) {
        this.loadOwnerPort = loadOwnerPort;
        this.saveOwnerPort = saveOwnerPort;
    }

    @Override
    public Owner update(
            Long id,
            String name,
            String email,
            String phone
    ) {

        loadOwnerPort.findOwnerById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("El dueño no existe")
                );

        Owner updatedOwner = new Owner(
                id,
                name,
                email,
                phone
        );

        return saveOwnerPort.saveOwner(updatedOwner);
    }
}