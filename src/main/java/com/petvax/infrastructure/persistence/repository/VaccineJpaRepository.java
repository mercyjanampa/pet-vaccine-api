package com.petvax.infrastructure.persistence.repository;

import com.petvax.infrastructure.persistence.entity.VaccineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineJpaRepository extends JpaRepository<VaccineEntity, Long> {

}