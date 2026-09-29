package com.petvax.application.port.out;

import com.petvax.domain.model.Vaccination;

public interface SaveVaccinationPort {

    // Guarda una vacunación y devuelve el resultado
    Vaccination save(Vaccination vaccination);
}