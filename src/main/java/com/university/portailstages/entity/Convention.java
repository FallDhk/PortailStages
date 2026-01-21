package com.university.portailstages.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Convention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Candidature candidature;

    @ManyToOne
    private User etudiant;

    @ManyToOne
    private User entreprise;

    @ManyToOne
    private OffreStage offre;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutConvention statut;

    private LocalDateTime signeEtudiantAt;
    private LocalDateTime signeEntrepriseAt;
    private LocalDateTime valideAdminAt;
}
