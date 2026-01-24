package com.university.portailstages.repository;

import com.university.portailstages.entity.Soutenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SoutenanceRepository extends JpaRepository<Soutenance, Long> {
    boolean existsByConventionId(Long id);

    Optional<Soutenance> findByConventionId(Long id);
}
