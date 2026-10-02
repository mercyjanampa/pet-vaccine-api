package com.petvax.application.port.in;

import com.petvax.domain.model.Vaccination;

import java.util.List;

public interface ListVaccinationsUseCase {

    List<Vaccination> findAll();
}