package com.university.portailstages.dto;

import lombok.Data;

@Data
public class EntrepriseReport {
    private long totalOffres;
    private long totalCandidatures;
    private long candidaturesAcceptees;
    private double moyenneEvaluations;
}
