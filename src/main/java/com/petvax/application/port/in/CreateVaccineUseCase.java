package com.petvax.application.port.in;

import com.petvax.application.command.CreateVaccineCommand;
import com.petvax.domain.model.Vaccine;

public interface CreateVaccineUseCase {

    Vaccine create(CreateVaccineCommand command);
}