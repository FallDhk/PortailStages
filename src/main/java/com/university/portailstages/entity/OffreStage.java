package com.university.portailstages.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class OffreStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Column(length = 2000)
    private String description;

    private String lieu;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private String filiereCible;
    private String niveauCible;

    @Column(length = 2000)
    private String missions;

    @Column(length = 2000)
    private String competences;

    @ManyToOne
    @JoinColumn(name = "entreprise_id")
    private User entreprise;

}
