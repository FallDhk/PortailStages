package com.university.portailstages.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Convention convention;

    @ManyToOne
    private User evaluateur; // Étudiant ou Entreprise

    private int note; // 0-20

    private String commentaire;

    private LocalDateTime dateEvaluation;
}
