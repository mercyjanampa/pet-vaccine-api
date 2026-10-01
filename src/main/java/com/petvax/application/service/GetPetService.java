package com.petvax.application.service;

import com.petvax.application.port.in.GetPetUseCase;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.domain.model.Pet;

public class GetPetService implements GetPetUseCase {

    private final LoadPetPort loadPetPort;

    public GetPetService(LoadPetPort loadPetPort) {
        this.loadPetPort = loadPetPort;
    }

    @Override
    public Pet findById(Long id) {

        return loadPetPort.findPetById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("La mascota no existe")
                );
    }
}