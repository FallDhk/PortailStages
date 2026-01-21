package com.university.portailstages.dto;

import lombok.Data;

@Data
public class AdminReport {
    private long totalConventions;
    private long enPreparation;
    private long signeeEtudiant;
    private long signeeEntreprise;
    private long valideAdmin;

    private long totalCandidatures;
    private long candidaturesAcceptees;

    private long totalOffres;

    private double moyenneEvaluations;
}
