package com.university.portailstages.service;

import com.university.portailstages.dto.EtudiantReport;
import com.university.portailstages.repository.CandidatureRepository;
import com.university.portailstages.repository.ConventionRepository;
import com.university.portailstages.repository.EvaluationRepository;
import com.university.portailstages.entity.StatutCandidature;
import com.university.portailstages.entity.StatutConvention;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EtudiantReportService {
    private final CandidatureRepository candidatureRepo;
    private final ConventionRepository conventionRepo;
    private final EvaluationRepository evaluationRepo;

    public EtudiantReport generate(String email) {

        EtudiantReport dto = new EtudiantReport();

        dto.setTotalCandidatures(candidatureRepo.countByEtudiantUserEmail(email));
        dto.setCandidaturesAcceptees(
                candidatureRepo.countByEtudiantUserEmailAndStatut(email, StatutCandidature.ACCEPTEE)
        );

        dto.setTotalConventions(conventionRepo.countByEtudiantEmail(email));
        dto.setConventionsValidees(
                conventionRepo.countByEtudiantEmailAndStatut(email, StatutConvention.VALIDEE_ADMIN)
        );

        Double avg = evaluationRepo.moyenneEtudiant(email);
        dto.setMoyenneEvaluations(avg != null ? avg : 0);

        return dto;
    }
}
