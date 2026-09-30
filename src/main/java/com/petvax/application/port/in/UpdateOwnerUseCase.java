package com.petvax.application.port.in;

import com.petvax.domain.model.Owner;

public interface UpdateOwnerUseCase {

    Owner update(Long id, String name, String email, String phone);
}