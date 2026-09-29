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

    public Pet(Long id,
               String name,
               Species species,
               String breed,
               LocalDate birthDate,
               String sex,
               Long ownerId) {

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