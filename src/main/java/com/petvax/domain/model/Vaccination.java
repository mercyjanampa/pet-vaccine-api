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

    public Vaccination(
            Long id,
            Long petId,
            Long vaccineId,
            LocalDate applicationDate,
            LocalDate nextDoseDate,
            String notes
    ) {

        // Toda vacunación debe pertenecer a una mascota
        if (petId == null) {
            throw new IllegalArgumentException(
                    "La mascota es obligatoria"
            );
        }

        // Debe indicarse la vacuna aplicada
        if (vaccineId == null) {
            throw new IllegalArgumentException(
                    "La vacuna es obligatoria"
            );
        }

        // La fecha de aplicación es necesaria
        if (applicationDate == null) {
            throw new IllegalArgumentException(
                    "La fecha de aplicación es obligatoria"
            );
        }

        // No permito registrar una vacunación aplicada en una fecha futura
        if (applicationDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La fecha de aplicación no puede estar en el futuro"
            );
        }

        // La próxima dosis no puede ser anterior a la aplicación
        if (nextDoseDate != null
                && nextDoseDate.isBefore(applicationDate)) {

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

        // Si todavía no hay una fecha para la siguiente dosis
        if (nextDoseDate == null) {
            return VaccinationStatus.PENDING;
        }

        LocalDate today = LocalDate.now();

        // La fecha ya pasó
        if (nextDoseDate.isBefore(today)) {
            return VaccinationStatus.EXPIRED;
        }

        long days = ChronoUnit.DAYS.between(today, nextDoseDate);

        // Si faltan siete días o menos, avisamos que está próxima
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