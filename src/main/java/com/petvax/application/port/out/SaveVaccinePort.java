package com.petvax.application.port.out;

import com.petvax.domain.model.Vaccine;

public interface SaveVaccinePort {

    Vaccine saveVaccine(Vaccine vaccine);
}