package com.petvax.infrastructure.notification;

import com.petvax.application.port.out.NotificationStrategy;
import com.petvax.domain.model.NotificationType;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationStrategy
        implements NotificationStrategy {

    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }

    @Override
    public void send(
            String recipient,
            String message
    ) {

        // Por ahora simulamos el envío.
        // Más adelante podría conectarse con un servicio real de correo.
        System.out.println(
                "[PetVax - Email] Para: "
                        + recipient
                        + " | "
                        + message
        );
    }
}