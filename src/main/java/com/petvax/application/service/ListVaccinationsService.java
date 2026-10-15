package com.petvax.application.service;

import com.petvax.application.port.in.ListVaccinationsUseCase;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.application.port.out.LoadVaccinationsPort;
import com.petvax.domain.model.Role;
import com.petvax.domain.model.User;
import com.petvax.domain.model.Vaccination;

import java.util.List;

public class ListVaccinationsService
        implements ListVaccinationsUseCase {

    private final LoadVaccinationsPort loadVaccinationsPort;
    private final LoadUserPort loadUserPort;

    public ListVaccinationsService(
            LoadVaccinationsPort loadVaccinationsPort,
            LoadUserPort loadUserPort
    ) {
        this.loadVaccinationsPort = loadVaccinationsPort;
        this.loadUserPort = loadUserPort;
    }

    @Override
    public List<Vaccination> findAll(String email) {

        User user = loadUserPort.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuario no encontrado"
                        )
                );

        if (user.getRole() == Role.ADMIN) {
            return loadVaccinationsPort.findAllVaccinations();
        }

        if (user.getOwnerId() == null) {
            throw new IllegalArgumentException(
                    "El usuario no tiene propietario asociado"
            );
        }

        return loadVaccinationsPort
                .findVaccinationsByOwnerId(
                        user.getOwnerId()
                );
    }
}