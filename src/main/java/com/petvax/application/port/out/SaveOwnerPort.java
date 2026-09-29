package com.petvax.application.port.out;

import com.petvax.domain.model.Owner;

public interface SaveOwnerPort {

    Owner saveOwner(Owner owner);
}