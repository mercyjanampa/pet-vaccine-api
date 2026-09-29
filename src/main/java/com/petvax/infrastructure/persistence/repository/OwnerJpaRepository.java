package com.petvax.infrastructure.persistence.repository;

import com.petvax.infrastructure.persistence.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerJpaRepository extends JpaRepository<OwnerEntity, Long> {

}