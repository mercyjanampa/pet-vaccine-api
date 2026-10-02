package com.petvax.infrastructure.persistence.repository;

import com.petvax.infrastructure.persistence.entity.VaccinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VaccinationJpaRepository
        extends JpaRepository<VaccinationEntity, Long> {

    List<VaccinationEntity> findByPetId(Long petId);

    @Query("""
            SELECT v
            FROM VaccinationEntity v
            WHERE v.nextDoseDate IS NOT NULL
              AND v.nextDoseDate < :today
            """)
    List<VaccinationEntity> findExpiredVaccinations(
            @Param("today") LocalDate today
    );
}