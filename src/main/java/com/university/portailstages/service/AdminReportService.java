package com.university.portailstages.service;

import com.university.portailstages.dto.AdminReport;
import com.university.portailstages.entity.StatutCandidature;
import com.university.portailstages.entity.StatutConvention;
import com.university.portailstages.repository.CandidatureRepository;
import com.university.portailstages.repository.ConventionRepository;
import com.university.portailstages.repository.EvaluationRepository;
import com.university.portailstages.repository.OffreStageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminReportService {
    private final ConventionRepository conventionRepo;
    private final CandidatureRepository candidatureRepo;
    private final OffreStageRepository offreRepo;
    private final EvaluationRepository evaluationRepo;

    public AdminReport generate() {

        AdminReport dto = new AdminReport();

        dto.setTotalConventions(conventionRepo.count());

        dto.setEnPreparation(conventionRepo.countByStatut(StatutConvention.EN_PREPARATION));
        dto.setSigneeEtudiant(conventionRepo.countByStatut(StatutConvention.SIGNEE_ETUDIANT));
        dto.setSigneeEntreprise(conventionRepo.countByStatut(StatutConvention.SIGNEE_ENTREPRISE));
        dto.setValideAdmin(conventionRepo.countByStatut(StatutConvention.VALIDEE_ADMIN));

        dto.setTotalCandidatures(candidatureRepo.count());
        dto.setCandidaturesAcceptees(candidatureRepo.countByStatut(StatutCandidature.ACCEPTEE));

        dto.setTotalOffres(offreRepo.count());

        Double avg = evaluationRepo.moyenne();
        dto.setMoyenneEvaluations(avg != null ? avg : 0);

        return dto;
    }
}
