package com.university.portailstages.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class RapportStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Convention convention;

    private String nomFichier;

    private String cheminFichier;

    private LocalDateTime dateDepot;

    private boolean valideEncadrant;

    private LocalDateTime valideAt;
}
