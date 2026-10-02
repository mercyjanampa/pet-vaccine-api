package com.petvax.domain.model;

public class Vaccine {

    private Long id;
    private String name;
    private Species species;
    private String description;
    private Integer recommendedIntervalMonths;

    public Vaccine(
            Long id,
            String name,
            Species species,
            String description,
            Integer recommendedIntervalMonths
    ) {

        // Toda vacuna debe tener un nombre
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la vacuna es obligatorio"
            );
        }

        // La especie permite saber con qué mascotas es compatible
        if (species == null) {
            throw new IllegalArgumentException(
                    "La especie de la vacuna es obligatoria"
            );
        }

        // Si se indica un intervalo, debe ser mayor a cero
        if (recommendedIntervalMonths != null
                && recommendedIntervalMonths <= 0) {

            throw new IllegalArgumentException(
                    "El intervalo recomendado debe ser mayor a cero"
            );
        }

        this.id = id;
        this.name = name;
        this.species = species;
        this.description = description;
        this.recommendedIntervalMonths = recommendedIntervalMonths;
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

    public String getDescription() {
        return description;
    }

    public Integer getRecommendedIntervalMonths() {
        return recommendedIntervalMonths;
    }
}