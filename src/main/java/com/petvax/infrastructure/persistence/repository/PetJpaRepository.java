package com.petvax.infrastructure.persistence.repository;

import com.petvax.infrastructure.persistence.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetJpaRepository extends JpaRepository<PetEntity, Long> {

}