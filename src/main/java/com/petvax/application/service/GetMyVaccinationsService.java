package com.petvax.application.service;

import com.petvax.application.port.in.GetMyVaccinationsUseCase;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.application.port.out.LoadVaccinationsPort;
import com.petvax.domain.model.User;
import com.petvax.domain.model.Vaccination;

import java.util.List;

public class GetMyVaccinationsService
        implements GetMyVaccinationsUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadVaccinationsPort loadVaccinationsPort;

    public GetMyVaccinationsService(
            LoadUserPort loadUserPort,
            LoadVaccinationsPort loadVaccinationsPort
    ) {
        this.loadUserPort = loadUserPort;
        this.loadVaccinationsPort = loadVaccinationsPort;
    }

    @Override
    public List<Vaccination> getMyVaccinations(String email) {

        User user = loadUserPort.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuario no encontrado"
                        )
                );

        if (user.getOwnerId() == null) {
            throw new IllegalArgumentException(
                    "El usuario no tiene propietario asociado"
            );
        }

        return loadVaccinationsPort
                .findVaccinationsByOwnerId(user.getOwnerId());
    }
}