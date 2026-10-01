package com.petvax.infrastructure.persistence.repository;

import com.petvax.domain.model.Species;
import com.petvax.infrastructure.persistence.entity.VaccineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaccineJpaRepository
        extends JpaRepository<VaccineEntity, Long> {

    List<VaccineEntity> findBySpecies(Species species);
}