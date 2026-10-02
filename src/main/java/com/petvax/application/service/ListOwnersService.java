package com.petvax.application.service;

import com.petvax.application.port.in.ListOwnersUseCase;
import com.petvax.application.port.out.LoadOwnerPort;
import com.petvax.domain.model.Owner;

import java.util.List;

public class ListOwnersService implements ListOwnersUseCase {

    private final LoadOwnerPort loadOwnerPort;

    public ListOwnersService(LoadOwnerPort loadOwnerPort) {
        this.loadOwnerPort = loadOwnerPort;
    }

    @Override
    public List<Owner> findAll() {
        return loadOwnerPort.findAllOwners();
    }
}