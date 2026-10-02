package com.petvax.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PetTest {

    @Test
    void shouldFailWhenBirthDateIsInTheFuture() {

        // Creo una fecha futura a propósito para comprobar la regla
        LocalDate futureDate = LocalDate.now().plusDays(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Pet(
                        1L,
                        "Luna",
                        Species.DOG,
                        "Labrador",
                        futureDate,
                        "F",
                        1L
                )
        );
    }

    @Test
    void shouldFailWhenPetHasNoOwner() {

        // Una mascota siempre debe estar relacionada con un dueño
        assertThrows(
                IllegalArgumentException.class,
                () -> new Pet(
                        1L,
                        "Luna",
                        Species.DOG,
                        "Labrador",
                        LocalDate.of(2023, 5, 10),
                        "F",
                        null
                )
        );
    }
}