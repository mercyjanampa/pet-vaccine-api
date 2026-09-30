package com.petvax.application.port.in;

import com.petvax.domain.model.Owner;

import java.util.List;

public interface ListOwnersUseCase {

    List<Owner> findAll();
}