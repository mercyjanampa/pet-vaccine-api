package com.petvax.application.factory;

import com.petvax.application.port.out.NotificationStrategy;
import com.petvax.domain.model.NotificationType;

import java.util.List;

public class NotificationStrategyFactory {

    private final List<NotificationStrategy> strategies;

    public NotificationStrategyFactory(
            List<NotificationStrategy> strategies
    ) {
        this.strategies = strategies;
    }

    public NotificationStrategy getStrategy(
            NotificationType type
    ) {

        return strategies.stream()
                .filter(strategy ->
                        strategy.getType() == type
                )
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Tipo de notificación no soportado"
                        )
                );
    }
}