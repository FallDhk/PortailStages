package com.university.portailstages.repository;

import com.university.portailstages.entity.Convention;
import com.university.portailstages.entity.StatutConvention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConventionRepository extends JpaRepository<Convention, Long> {
    List<Convention> findByEtudiantId(Long id);
    List<Convention> findByEntrepriseId(Long id);

    List<Convention> findByEncadrantPedagogiqueIsNull();
    //List<Convention> findByEncadrantPedagogiqueId(Long id);

    boolean existsByCandidatureId(Long id);

    long countByStatut(StatutConvention statut);

    long countByEtudiantEmail(String email);

    long countByEtudiantEmailAndStatut(String email, StatutConvention statut);

    long countByEntrepriseEmailAndStatut(String email, StatutConvention statut);


}
