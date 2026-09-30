package com.petvax.application.service;

import com.petvax.application.port.in.ListOwnersUseCase;
import com.petvax.application.port.out.LoadOwnersPort;
import com.petvax.domain.model.Owner;

import java.util.List;

public class ListOwnersService implements ListOwnersUseCase {

    private final LoadOwnersPort loadOwnersPort;

    public ListOwnersService(LoadOwnersPort loadOwnersPort) {
        this.loadOwnersPort = loadOwnersPort;
    }

    @Override
    public List<Owner> findAll() {

        return loadOwnersPort.findAllOwners();
    }
}