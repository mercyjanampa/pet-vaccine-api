package com.petvax.application.port.in;

import com.petvax.application.command.CreatePetCommand;
import com.petvax.domain.model.Pet;

public interface CreatePetUseCase {

    Pet create(CreatePetCommand command);
}