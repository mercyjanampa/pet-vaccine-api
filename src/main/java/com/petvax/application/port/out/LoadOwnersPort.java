package com.petvax.application.port.out;

import com.petvax.domain.model.Owner;

import java.util.List;

public interface LoadOwnersPort {

    List<Owner> findAllOwners();
}