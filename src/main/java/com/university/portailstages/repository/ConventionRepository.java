package com.university.portailstages.repository;

import com.university.portailstages.entity.Convention;
import com.university.portailstages.entity.StatutConvention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConventionRepository extends JpaRepository<Convention, Long> {
    List<Convention> findByEtudiantId(Long id);
    List<Convention> findByEntrepriseId(Long id);

    List<Convention> findByEntrepriseIdAndValideAdminAtIsNotNull(Long entrepriseId);

    List<Convention> findByEncadrantPedagogiqueIsNull();
    //List<Convention> findByEncadrantPedagogiqueId(Long id);
    List<Convention> findAll();

    List<Convention> findByEtudiantIdAndStatut(Long etudiantId, StatutConvention statut);

    List<Convention> findByEtudiantIdAndValideAdminAtIsNotNull(Long etudiantId);

    List<Convention> findByEncadrantPedagogiqueId(Long id);

    List<Convention> findByStatut(StatutConvention statut);

    boolean existsByCandidatureId(Long id);

    long countByStatut(StatutConvention statut);

    long countByEtudiantEmail(String email);

    long countByEtudiantEmailAndStatut(String email, StatutConvention statut);

    long countByEntrepriseEmailAndStatut(String email, StatutConvention statut);

    @Query("""
    select c from Convention c
    join RapportStage r on r.convention.id = c.id
    where r.valideEncadrant = true
    """)
    List<Convention> findReadyForSoutenance();


}
