package com.university.portailstages.repository;

import com.university.portailstages.entity.RapportStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RapportStageRepository extends JpaRepository<RapportStage, Long> {
    boolean existsByConventionId(Long id);

    Optional<RapportStage> findByConventionId(Long id);
}
