package com.petvax.application.port.in;

import com.petvax.domain.model.Vaccine;

import java.util.List;

public interface ListVaccinesUseCase {

    List<Vaccine> findAll();
}