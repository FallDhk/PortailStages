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

    // --- Académique ---
    private String filiere;
    private String niveau;
    //private String anneeUniversitaire;

    @Column(length = 2000)
    private String missions;

    @Column(length = 2000)
    private String competences;

    // --- Encadrement ---
    @ManyToOne
    private User encadrantPedagogique;

    private String tuteurEntreprise;

    @Enumerated(EnumType.STRING)
    private StatutConvention statut;

    private LocalDateTime signeEtudiantAt;
    private LocalDateTime signeEntrepriseAt;
    private LocalDateTime valideAdminAt;

}
