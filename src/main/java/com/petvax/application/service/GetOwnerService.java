package com.petvax.application.service;

import com.petvax.application.port.in.GetOwnerUseCase;
import com.petvax.application.port.out.LoadOwnerPort;
import com.petvax.domain.model.Owner;

public class GetOwnerService implements GetOwnerUseCase {

    private final LoadOwnerPort loadOwnerPort;

    public GetOwnerService(LoadOwnerPort loadOwnerPort) {
        this.loadOwnerPort = loadOwnerPort;
    }

    @Override
    public Owner findById(Long id) {

        return loadOwnerPort.findOwnerById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("El dueño no existe")
                );
    }
}