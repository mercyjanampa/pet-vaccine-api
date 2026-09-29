package com.petvax.infrastructure.persistence.repository;

import com.petvax.infrastructure.persistence.entity.VaccinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinationJpaRepository extends JpaRepository<VaccinationEntity, Long> {

}