package com.university.portailstages.dto;

import lombok.Data;

@Data
public class EtudiantReport {
    private long totalCandidatures;
    private long candidaturesAcceptees;
    private long totalConventions;
    private long conventionsValidees;
    private double moyenneEvaluations;
}
