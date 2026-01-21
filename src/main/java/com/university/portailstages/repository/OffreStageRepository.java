package com.university.portailstages.repository;

import com.university.portailstages.entity.OffreStage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OffreStageRepository extends JpaRepository<OffreStage, Long> {

    //Pagination entreprise
    Page<OffreStage> findByEntrepriseId(Long entrepriseId, Pageable pageable);

    //Compteur
    long countByEntrepriseEmail(String email);

}
