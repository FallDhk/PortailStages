package com.university.portailstages.service;

import com.university.portailstages.dto.EntrepriseReport;
import com.university.portailstages.repository.CandidatureRepository;
import com.university.portailstages.repository.EvaluationRepository;
import com.university.portailstages.repository.OffreStageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntrepriseReportService {
    private final OffreStageRepository offreRepo;
    private final CandidatureRepository candidatureRepo;
    private final EvaluationRepository evaluationRepo;

    public EntrepriseReport generate(String email) {

        EntrepriseReport dto = new EntrepriseReport();

        dto.setTotalOffres(offreRepo.countByEntrepriseEmail(email));
        dto.setTotalCandidatures(candidatureRepo.countByEntreprise(email));
        dto.setCandidaturesAcceptees(candidatureRepo.countAcceptees(email));

        Double avg = evaluationRepo.moyenneEntreprise(email);
        dto.setMoyenneEvaluations(avg != null ? avg : 0);

        return dto;
    }
}
