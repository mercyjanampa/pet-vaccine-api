package com.petvax.application.port.in;

import com.petvax.application.command.CreateOwnerCommand;
import com.petvax.domain.model.Owner;

public interface CreateOwnerUseCase {

    Owner create(CreateOwnerCommand command);
}