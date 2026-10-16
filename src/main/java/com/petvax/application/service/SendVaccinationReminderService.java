package com.petvax.application.service;

import com.petvax.application.factory.NotificationStrategyFactory;
import com.petvax.application.port.in.SendVaccinationReminderUseCase;
import com.petvax.application.port.out.LoadPetPort;
import com.petvax.application.port.out.LoadUserPort;
import com.petvax.application.port.out.LoadVaccinationsPort;
import com.petvax.application.port.out.NotificationStrategy;
import com.petvax.domain.model.*;

public class SendVaccinationReminderService
        implements SendVaccinationReminderUseCase {

    private final LoadVaccinationsPort loadVaccinationsPort;
    private final LoadPetPort loadPetPort;
    private final LoadUserPort loadUserPort;
    private final NotificationStrategyFactory notificationStrategyFactory;

    public SendVaccinationReminderService(
            LoadVaccinationsPort loadVaccinationsPort,
            LoadPetPort loadPetPort,
            LoadUserPort loadUserPort,
            NotificationStrategyFactory notificationStrategyFactory
    ) {
        this.loadVaccinationsPort = loadVaccinationsPort;
        this.loadPetPort = loadPetPort;
        this.loadUserPort = loadUserPort;
        this.notificationStrategyFactory =
                notificationStrategyFactory;
    }

    @Override
    public void sendReminder(
            Long vaccinationId,
            String email,
            NotificationType type
    ) {

        Vaccination vaccination =
                loadVaccinationsPort
                        .findVaccinationById(vaccinationId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "La vacunación no existe"
                                )
                        );

        User user = loadUserPort
                .findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuario no encontrado"
                        )
                );

        Pet pet = loadPetPort
                .findPetById(vaccination.getPetId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La mascota no existe"
                        )
                );

        if (user.getRole() != Role.ADMIN) {

            if (user.getOwnerId() == null
                    || !user.getOwnerId().equals(pet.getOwnerId())) {

                throw new IllegalArgumentException(
                        "No tienes acceso a esta vacunación"
                );
            }
        }

        String message =
                "Recordatorio para "
                        + pet.getName()
                        + ": "
                        + vaccination.getRecommendedAction();

        NotificationStrategy strategy =
                notificationStrategyFactory
                        .getStrategy(type);

        strategy.send(
                user.getEmail(),
                message
        );
    }
}