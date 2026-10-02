package com.petvax.application.port.in;

import com.petvax.domain.model.Vaccination;

public interface GetVaccinationUseCase {

    Vaccination findById(Long id);
}