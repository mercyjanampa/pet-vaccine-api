package com.petvax.infrastructure.persistence.entity;

import com.petvax.domain.model.VaccinationSource;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "vaccinations")
public class VaccinationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private PetEntity pet;

    @ManyToOne
    @JoinColumn(name = "vaccine_id", nullable = true)
    private VaccineEntity vaccine;

    @Column(name = "custom_vaccine_name")
    private String customVaccineName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VaccinationSource source;

    private LocalDate applicationDate;
    private LocalDate nextDoseDate;

    private String notes;

    public VaccinationEntity() {
    }

    public VaccinationEntity(
            Long id,
            PetEntity pet,
            VaccineEntity vaccine,
            String customVaccineName,
            VaccinationSource source,
            LocalDate applicationDate,
            LocalDate nextDoseDate,
            String notes
    ) {
        this.id = id;
        this.pet = pet;
        this.vaccine = vaccine;
        this.customVaccineName = customVaccineName;
        this.source = source;
        this.applicationDate = applicationDate;
        this.nextDoseDate = nextDoseDate;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public PetEntity getPet() {
        return pet;
    }

    public VaccineEntity getVaccine() {
        return vaccine;
    }

    public String getCustomVaccineName() {
        return customVaccineName;
    }

    public VaccinationSource getSource() {
        return source;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public LocalDate getNextDoseDate() {
        return nextDoseDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPet(PetEntity pet) {
        this.pet = pet;
    }

    public void setVaccine(VaccineEntity vaccine) {
        this.vaccine = vaccine;
    }

    public void setCustomVaccineName(String customVaccineName) {
        this.customVaccineName = customVaccineName;
    }

    public void setSource(VaccinationSource source) {
        this.source = source;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public void setNextDoseDate(LocalDate nextDoseDate) {
        this.nextDoseDate = nextDoseDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}