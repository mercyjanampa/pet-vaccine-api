package com.petvax.application.port.out;

import com.petvax.domain.model.NotificationType;

public interface NotificationStrategy {

    NotificationType getType();

    void send(
            String recipient,
            String message
    );
}