package com.petvax.application.factory;

import com.petvax.application.port.out.NotificationStrategy;
import com.petvax.domain.model.NotificationType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NotificationStrategyFactoryTest {

    @Test
    void shouldSelectLogStrategy() {

        NotificationStrategy logStrategy =
                mock(NotificationStrategy.class);

        NotificationStrategy emailStrategy =
                mock(NotificationStrategy.class);

        when(logStrategy.getType())
                .thenReturn(NotificationType.LOG);

        when(emailStrategy.getType())
                .thenReturn(NotificationType.EMAIL);

        NotificationStrategyFactory factory =
                new NotificationStrategyFactory(
                        List.of(
                                logStrategy,
                                emailStrategy
                        )
                );

        NotificationStrategy result =
                factory.getStrategy(NotificationType.LOG);

        assertSame(logStrategy, result);
    }

    @Test
    void shouldSelectEmailStrategy() {

        NotificationStrategy logStrategy =
                mock(NotificationStrategy.class);

        NotificationStrategy emailStrategy =
                mock(NotificationStrategy.class);

        when(logStrategy.getType())
                .thenReturn(NotificationType.LOG);

        when(emailStrategy.getType())
                .thenReturn(NotificationType.EMAIL);

        NotificationStrategyFactory factory =
                new NotificationStrategyFactory(
                        List.of(
                                logStrategy,
                                emailStrategy
                        )
                );

        NotificationStrategy result =
                factory.getStrategy(NotificationType.EMAIL);

        assertSame(emailStrategy, result);
    }
}