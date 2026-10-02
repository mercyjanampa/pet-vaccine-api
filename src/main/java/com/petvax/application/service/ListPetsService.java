package com.petvax.application.service;

import com.petvax.application.port.in.ListPetsUseCase;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.domain.model.Pet;

import java.util.List;

public class ListPetsService implements ListPetsUseCase {

    private final LoadPetPort loadPetPort;

    public ListPetsService(LoadPetPort loadPetPort) {
        this.loadPetPort = loadPetPort;
    }

    @Override
    public List<Pet> findAll() {

        return loadPetPort.findAllPets();
    }
}