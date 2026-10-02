package com.petvax.application.service;

import com.petvax.application.port.in.ListVaccinationsByPetUseCase;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadVaccinationsPort;
import com.petvax.domain.model.Vaccination;

import java.util.List;

public class ListVaccinationsByPetService
        implements ListVaccinationsByPetUseCase {

    private final LoadPetPort loadPetPort;
    private final LoadVaccinationsPort loadVaccinationsPort;

    public ListVaccinationsByPetService(
            LoadPetPort loadPetPort,
            LoadVaccinationsPort loadVaccinationsPort
    ) {
        this.loadPetPort = loadPetPort;
        this.loadVaccinationsPort = loadVaccinationsPort;
    }

    @Override
    public List<Vaccination> findByPetId(Long petId) {

        loadPetPort.findPetById(petId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La mascota no existe"
                        )
                );

        return loadVaccinationsPort
                .findVaccinationsByPetId(petId);
    }
}