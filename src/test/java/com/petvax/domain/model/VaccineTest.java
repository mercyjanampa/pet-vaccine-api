package com.petvax.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class VaccineTest {

    @Test
    void shouldFailWhenRecommendedIntervalIsNegative() {

        // Un intervalo negativo no tendría sentido para una vacuna
        assertThrows(
                IllegalArgumentException.class,
                () -> new Vaccine(
                        1L,
                        "Vacuna de prueba",
                        Species.DOG,
                        "Prueba",
                        -12
                )
        );
    }

    @Test
    void shouldFailWhenVaccineHasNoSpecies() {

        // La especie es necesaria para validar compatibilidad con la mascota
        assertThrows(
                IllegalArgumentException.class,
                () -> new Vaccine(
                        1L,
                        "Vacuna de prueba",
                        null,
                        "Prueba",
                        12
                )
        );
    }
}