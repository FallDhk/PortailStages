package com.university.portailstages.repository;

import com.university.portailstages.entity.ProfilEtudiant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfilEtudiantRepository extends JpaRepository<ProfilEtudiant, Long> {
    Optional<ProfilEtudiant> findByUserEmail(String email);

    boolean existsByUserEmail(String email);
}
