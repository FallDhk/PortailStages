package com.university.portailstages.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProfilEtudiant etudiant;

    @ManyToOne
    private OffreStage offre;

    private LocalDate dateCandidature;

    @Enumerated(EnumType.STRING)
    private StatutCandidature statut;
}
