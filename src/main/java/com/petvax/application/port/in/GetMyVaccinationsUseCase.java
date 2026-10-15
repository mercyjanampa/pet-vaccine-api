package com.petvax.application.port.in;

import com.petvax.domain.model.Vaccination;

import java.util.List;

public interface GetMyVaccinationsUseCase {

    List<Vaccination> getMyVaccinations(String email);
}