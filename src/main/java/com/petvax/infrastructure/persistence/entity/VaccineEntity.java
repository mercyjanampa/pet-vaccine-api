package com.petvax.infrastructure.persistence.entity;

import com.petvax.domain.model.Species;
import jakarta.persistence.*;

@Entity
@Table(name = "vaccines")
public class VaccineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Species species;

    private String description;

    private Integer recommendedIntervalMonths;

    public VaccineEntity() {
        // JPA necesita este constructor vacío
    }

    public VaccineEntity(
            Long id,
            String name,
            Species species,
            String description,
            Integer recommendedIntervalMonths
    ) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRecommendedIntervalMonths(Integer recommendedIntervalMonths) {
        this.recommendedIntervalMonths = recommendedIntervalMonths;
    }
}