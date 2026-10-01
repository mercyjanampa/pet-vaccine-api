package com.petvax.application.port.in;

import com.petvax.domain.model.Pet;

public interface GetPetUseCase {

    Pet findById(Long id);
}