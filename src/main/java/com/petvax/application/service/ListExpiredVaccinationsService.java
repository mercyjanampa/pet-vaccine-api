package com.petvax.application.service;

import com.petvax.application.port.in.ListExpiredVaccinationsUseCase;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.application.port.out.LoadVaccinationsPort;
import com.petvax.domain.model.Role;
import com.petvax.domain.model.User;
import com.petvax.domain.model.Vaccination;
import com.petvax.domain.model.VaccinationStatus;

import java.util.List;

public class ListExpiredVaccinationsService
        implements ListExpiredVaccinationsUseCase {

    private final LoadVaccinationsPort loadVaccinationsPort;
    private final LoadUserPort loadUserPort;

    public ListExpiredVaccinationsService(
            LoadVaccinationsPort loadVaccinationsPort,
            LoadUserPort loadUserPort
    ) {
        this.loadVaccinationsPort = loadVaccinationsPort;
        this.loadUserPort = loadUserPort;
    }

    @Override
    public List<Vaccination> findExpired(String email) {

        User user = loadUserPort.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuario no encontrado"
                        )
                );

        // ADMIN puede ver todas las vacunaciones vencidas
        if (user.getRole() == Role.ADMIN) {
            return loadVaccinationsPort.findExpiredVaccinations();
        }

        // USER solo ve las vacunaciones vencidas de sus mascotas
        if (user.getOwnerId() == null) {
            return List.of();
        }

        return loadVaccinationsPort
                .findVaccinationsByOwnerId(user.getOwnerId())
                .stream()
                .filter(vaccination ->
                        vaccination.getStatus()
                                == VaccinationStatus.EXPIRED
                )
                .toList();
    }
}