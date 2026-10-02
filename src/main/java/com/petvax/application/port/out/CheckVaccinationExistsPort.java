package com.petvax.application.port.out;

import java.time.LocalDate;

public interface CheckVaccinationExistsPort {

    boolean existsByPetIdAndVaccineIdAndApplicationDate(
            Long petId,
            Long vaccineId,
            LocalDate applicationDate
    );
}