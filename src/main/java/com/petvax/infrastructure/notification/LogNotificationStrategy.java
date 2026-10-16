package com.petvax.infrastructure.notification;

import com.petvax.application.port.out.NotificationStrategy;
import com.petvax.domain.model.NotificationType;
import org.springframework.stereotype.Component;

@Component
public class LogNotificationStrategy
        implements NotificationStrategy {

    @Override
    public NotificationType getType() {
        return NotificationType.LOG;
    }

    @Override
    public void send(
            String recipient,
            String message
    ) {

        System.out.println(
                "[PetVax - Recordatorio] "
                        + recipient
                        + " - "
                        + message
        );
    }
}