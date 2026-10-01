package com.petvax.application.service;

import com.petvax.application.port.in.DeletePetUseCase;
import com.petvax.application.port.out.DeletePetPort;
import com.petvax.application.port.out.LoadPetPort;

public class DeletePetService implements DeletePetUseCase {

    private final LoadPetPort loadPetPort;
    private final DeletePetPort deletePetPort;

    public DeletePetService(
            LoadPetPort loadPetPort,
            DeletePetPort deletePetPort
    ) {
        this.loadPetPort = loadPetPort;
        this.deletePetPort = deletePetPort;
    }

    @Override
    public void delete(Long id) {

        loadPetPort.findPetById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("La mascota no existe")
                );

        deletePetPort.deletePetById(id);
    }
}