package com.university.portailstages.repository;

import com.university.portailstages.entity.Candidature;
import com.university.portailstages.entity.StatutCandidature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {

    List<Candidature> findByEtudiantId(Long id);

    List<Candidature> findByOffreId(Long id);

    Optional<Candidature> findByOffreIdAndEtudiantId(Long offreId, Long etudiantId);

    long countByEtudiantUserEmail(String email);

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

    long countByEtudiantUserEmailAndStatut(String email, StatutCandidature statut);

    boolean existsByEtudiantIdAndOffreId(Long etudiantId, Long offreId);

}


