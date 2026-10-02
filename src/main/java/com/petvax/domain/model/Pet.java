package com.petvax.domain.model;

import java.time.LocalDate;

public class Pet {

    private Long id;
    private String name;
    private Species species;
    private String breed;
    private LocalDate birthDate;
    private String sex;
    private Long ownerId;

    public Pet(
            Long id,
            String name,
            Species species,
            String breed,
            LocalDate birthDate,
            String sex,
            Long ownerId
    ) {

        // Una mascota debe tener un nombre válido
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la mascota es obligatorio"
            );
        }

        // La especie es necesaria para validar vacunas compatibles
        if (species == null) {
            throw new IllegalArgumentException(
                    "La especie de la mascota es obligatoria"
            );
        }

        // No tendría sentido registrar una mascota nacida en el futuro
        if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La fecha de nacimiento no puede estar en el futuro"
            );
        }

        // Toda mascota debe estar asociada a un dueño
        if (ownerId == null) {
            throw new IllegalArgumentException(
                    "La mascota debe tener un dueño"
            );
        }

        this.id = id;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.birthDate = birthDate;
        this.sex = sex;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Species getSpecies() {
        return species;
    }

    public String getBreed() {
        return breed;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getSex() {
        return sex;
    }

    public Long getOwnerId() {
        return ownerId;
    }
}