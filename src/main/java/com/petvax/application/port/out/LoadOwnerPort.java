package com.petvax.application.port.out;

import com.petvax.domain.model.Owner;

import java.util.List;
import java.util.Optional;

public interface LoadOwnerPort {

    // Busca un dueño por id
    Optional<Owner> findOwnerById(Long id);

    // Consulta todos los dueños
    List<Owner> findAllOwners();
}