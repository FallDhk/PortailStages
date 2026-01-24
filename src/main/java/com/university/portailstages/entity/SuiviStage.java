package com.university.portailstages.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class SuiviStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Convention convention;

    private LocalDate date;

    @Column(length = 3000)
    private String contenu;

    @Column(length = 1500)
    private String commentaireEncadrant;

    private Integer progression; // %

    private boolean valide;

    private LocalDateTime creeAt;
}
