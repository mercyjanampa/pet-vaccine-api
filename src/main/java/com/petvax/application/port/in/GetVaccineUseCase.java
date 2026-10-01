package com.petvax.application.port.in;

import com.petvax.domain.model.Vaccine;

public interface GetVaccineUseCase {

    Vaccine findById(Long id);
}