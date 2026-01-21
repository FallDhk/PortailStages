package com.university.portailstages.repository;

import com.university.portailstages.entity.Candidature;
import com.university.portailstages.entity.StatutCandidature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {
    List<Candidature> findByEtudiantId(Long id);

    List<Candidature> findByOffreId(Long id);

    long countByEtudiantEmail(String email);

    long countByOffreEntrepriseEmail(String email);

    long countByStatut(StatutCandidature statut);

    @Query("""
        select count(c)
        from Candidature c
        where c.offre.entreprise.email = :email
    """)
    long countByEntreprise(@Param("email") String email);


    @Query(""" 
        select count(c)
        from Candidature c
        where c.offre.entreprise.email = :email
        and c.statut = 'ACCEPTEE'
    """)
    long countAcceptees(@Param("email") String email);

    long countByEtudiantEmailAndStatut(String email, StatutCandidature statut);
}

