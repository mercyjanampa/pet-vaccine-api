package com.petvax.application.port.in;

import com.petvax.domain.model.Owner;

public interface GetOwnerUseCase {

    Owner findById(Long id);
}