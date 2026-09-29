package com.petvax.application.service;

import com.petvax.application.command.CreateOwnerCommand;
import com.petvax.application.port.in.CreateOwnerUseCase;
import com.petvax.application.port.out.SaveOwnerPort;
import com.petvax.domain.model.Owner;

public class CreateOwnerService implements CreateOwnerUseCase {

    private final SaveOwnerPort saveOwnerPort;

    public CreateOwnerService(SaveOwnerPort saveOwnerPort) {
        this.saveOwnerPort = saveOwnerPort;
    }

    @Override
    public Owner create(CreateOwnerCommand command) {

        Owner owner = new Owner(
                null,
                command.name(),
                command.email(),
                command.phone()
        );

        return saveOwnerPort.saveOwner(owner);
    }
}