package com.petvax.application.service;

import com.petvax.application.port.in.DeleteOwnerUseCase;
import com.petvax.application.port.out.DeleteOwnerPort;
import com.petvax.application.port.out.LoadOwnerPort;

public class DeleteOwnerService implements DeleteOwnerUseCase {

    private final LoadOwnerPort loadOwnerPort;
    private final DeleteOwnerPort deleteOwnerPort;

    public DeleteOwnerService(
            LoadOwnerPort loadOwnerPort,
            DeleteOwnerPort deleteOwnerPort
    ) {
        this.loadOwnerPort = loadOwnerPort;
        this.deleteOwnerPort = deleteOwnerPort;
    }

    @Override
    public void delete(Long id) {

        loadOwnerPort.findOwnerById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("El dueño no existe")
                );

        deleteOwnerPort.deleteOwnerById(id);
    }
}