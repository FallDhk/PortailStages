package com.university.portailstages.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Soutenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Convention convention;

    private LocalDateTime dateSoutenance;

    private String salle;

    private String jury;

    private Double noteFinale;

    private boolean valide;

    private LocalDateTime valideAt;
}
