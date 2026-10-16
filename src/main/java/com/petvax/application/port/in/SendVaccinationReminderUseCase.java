package com.petvax.application.port.in;

import com.petvax.domain.model.NotificationType;

public interface SendVaccinationReminderUseCase {

    void sendReminder(
            Long vaccinationId,
            String email,
            NotificationType type
    );
}