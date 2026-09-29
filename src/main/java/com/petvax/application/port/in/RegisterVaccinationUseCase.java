package com.petvax.application.port.in;

import com.petvax.application.command.RegisterVaccinationCommand;
import com.petvax.domain.model.Vaccination;

public interface RegisterVaccinationUseCase {

    Vaccination register(RegisterVaccinationCommand command);
}