package com.petvax.domain.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Vaccination {

    private Long id;
    private Long petId;
    private Long vaccineId;
    private LocalDate applicationDate;
    private LocalDate nextDoseDate;
    private String notes;

    public Vaccination(Long id,
                       Long petId,
                       Long vaccineId,
                       LocalDate applicationDate,
                       LocalDate nextDoseDate,
                       String notes) {

        if (applicationDate == null) {
            throw new IllegalArgumentException(
                    "La fecha de aplicación es obligatoria"
            );
        }

        if (nextDoseDate != null &&
                nextDoseDate.isBefore(applicationDate)) {

            throw new IllegalArgumentException(
                    "La próxima dosis no puede ser anterior a la fecha de aplicación"
            );
        }

        this.id = id;
        this.petId = petId;
        this.vaccineId = vaccineId;
        this.applicationDate = applicationDate;
        this.nextDoseDate = nextDoseDate;
        this.notes = notes;
    }

    public VaccinationStatus getStatus() {

        if (nextDoseDate == null) {
            return VaccinationStatus.PENDING;
        }

        LocalDate today = LocalDate.now();

        if (nextDoseDate.isBefore(today)) {
            return VaccinationStatus.EXPIRED;
        }

        long days = ChronoUnit.DAYS.between(today, nextDoseDate);

        if (days <= 7) {
            return VaccinationStatus.UPCOMING;
        }

        return VaccinationStatus.UP_TO_DATE;
    }

    public String getRecommendedAction() {

        if (getStatus() == VaccinationStatus.EXPIRED) {
            return "Programar una consulta veterinaria para evaluar y reprogramar la dosis.";
        }

        if (getStatus() == VaccinationStatus.UPCOMING) {
            return "Revisar la fecha y preparar la próxima vacunación.";
        }

        if (getStatus() == VaccinationStatus.UP_TO_DATE) {
            return "No se requiere ninguna acción por el momento.";
        }

        return "Registrar o programar la siguiente dosis.";
    }

    public Long getId() {
        return id;
    }

    public Long getPetId() {
        return petId;
    }

    public Long getVaccineId() {
        return vaccineId;
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
}