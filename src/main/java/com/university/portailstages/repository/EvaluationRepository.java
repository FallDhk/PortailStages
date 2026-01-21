package com.university.portailstages.repository;

import com.university.portailstages.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByConventionId(Long conventionId);

    List<Evaluation> findByEvaluateurEmail(String email);

    @Query("select avg(e.note) from Evaluation e")
    Double moyenne();

    @Query("""
        select avg(e.note)
        from Evaluation e
        where e.convention.entreprise.email = :email
    """)
    Double moyenneEntreprise(@Param("email") String email);

    @Query("""
        select avg(e.note)
        from Evaluation e
        where e.convention.etudiant.email = :email
    """)
    Double moyenneEtudiant(@Param("email") String email);
}
