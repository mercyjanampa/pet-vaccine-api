package com.petvax.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VaccinationTest {

    @Test
    void shouldReturnExpiredWhenNextDoseDateIsInThePast() {

        // Preparo una vacuna cuya próxima dosis ya venció
        LocalDate applicationDate = LocalDate.now().minusMonths(12);
        LocalDate nextDoseDate = LocalDate.now().minusDays(1);

        Vaccination vaccination = new Vaccination(
                1L,
                1L,
                1L,
                applicationDate,
                nextDoseDate,
                "Vacuna de prueba"
        );

        // Consulto el estado de la vacunación
        VaccinationStatus status = vaccination.getStatus();

        // Verifico que el sistema la marque como vencida
        assertEquals(VaccinationStatus.EXPIRED, status);
    }

    @Test
    void shouldReturnUpcomingWhenNextDoseIsWithinSevenDays() {

        // Preparo una vacuna cuya siguiente dosis será pronto
        LocalDate applicationDate = LocalDate.now().minusMonths(1);
        LocalDate nextDoseDate = LocalDate.now().plusDays(5);

        Vaccination vaccination = new Vaccination(
                1L,
                1L,
                1L,
                applicationDate,
                nextDoseDate,
                "Próxima vacuna"
        );

        // Consulto el estado
        VaccinationStatus status = vaccination.getStatus();

        // Debe aparecer como próxima
        assertEquals(VaccinationStatus.UPCOMING, status);
    }

    @Test
    void shouldRejectNextDoseBeforeApplicationDate() {

        // Creo fechas incorrectas a propósito
        LocalDate applicationDate = LocalDate.now();
        LocalDate nextDoseDate = LocalDate.now().minusDays(1);

        // Verifico que el sistema no permita esa fecha
        assertThrows(
                IllegalArgumentException.class,
                () -> new Vaccination(
                        1L,
                        1L,
                        1L,
                        applicationDate,
                        nextDoseDate,
                        "Fecha inválida"
                )
        );
    }
    @Test
    void shouldFailWhenApplicationDateIsInTheFuture() {

        // No debería poder registrarse una vacunación como aplicada en el futuro
        LocalDate applicationDate = LocalDate.now().plusDays(1);
        LocalDate nextDoseDate = LocalDate.now().plusMonths(12);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Vaccination(
                        1L,
                        1L,
                        1L,
                        applicationDate,
                        nextDoseDate,
                        "Fecha futura"
                )
        );
    }
}