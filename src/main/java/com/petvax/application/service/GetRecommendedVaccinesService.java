package com.petvax.application.service;

import com.petvax.application.port.in.GetRecommendedVaccinesUseCase;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.application.port.out.LoadVaccinePort;
import com.petvax.domain.model.Pet;
import com.petvax.domain.model.Role;
import com.petvax.domain.model.User;
import com.petvax.domain.model.Vaccine;

import java.util.List;

public class GetRecommendedVaccinesService
        implements GetRecommendedVaccinesUseCase {

    private final LoadPetPort loadPetPort;
    private final LoadUserPort loadUserPort;
    private final LoadVaccinePort loadVaccinePort;

    public GetRecommendedVaccinesService(
            LoadPetPort loadPetPort,
            LoadUserPort loadUserPort,
            LoadVaccinePort loadVaccinePort
    ) {
        this.loadPetPort = loadPetPort;
        this.loadUserPort = loadUserPort;
        this.loadVaccinePort = loadVaccinePort;
    }

    @Override
    public List<Vaccine> getRecommendedVaccines(
            Long petId,
            String email
    ) {

        Pet pet = loadPetPort.findPetById(petId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La mascota no existe"
                        )
                );

        User user = loadUserPort.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuario no encontrado"
                        )
                );

        // ADMIN puede consultar cualquier mascota
        if (user.getRole() != Role.ADMIN) {

            if (user.getOwnerId() == null
                    || !user.getOwnerId().equals(pet.getOwnerId())) {

                throw new IllegalArgumentException(
                        "No tienes acceso a esta mascota"
                );
            }
        }

        return loadVaccinePort.findVaccinesBySpecies(
                pet.getSpecies()
        );
    }
}