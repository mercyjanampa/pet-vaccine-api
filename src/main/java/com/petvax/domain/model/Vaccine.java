package com.petvax.domain.model;

public class Vaccine {

    private Long id;
    private String name;
    private Species species;
    private String description;
    private Integer recommendedIntervalMonths;

    public Vaccine(Long id,
                   String name,
                   Species species,
                   String description,
                   Integer recommendedIntervalMonths) {

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