package com.petvax.application.port.out;

import com.petvax.domain.model.Vaccine;

import java.util.List;

public interface LoadVaccinesPort {

    List<Vaccine> findAllVaccines();
}