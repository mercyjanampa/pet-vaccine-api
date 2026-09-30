package com.petvax.application.service;

import com.petvax.application.port.in.ListPetsUseCase;
import com.petvax.application.port.out.LoadPetsPort;
import com.petvax.domain.model.Pet;

import java.util.List;

public class ListPetsService implements ListPetsUseCase {

    private final LoadPetsPort loadPetsPort;

    public ListPetsService(LoadPetsPort loadPetsPort) {
        this.loadPetsPort = loadPetsPort;
    }

    @Override
    public List<Pet> findAll() {

        return loadPetsPort.findAllPets();
    }
}