package com.petvax.application.command;

import java.time.LocalDate;

public record RegisterVaccinationCommand(
        Long petId,
        Long vaccineId,
        LocalDate applicationDate,
        LocalDate nextDoseDate,
        String notes
) {
}