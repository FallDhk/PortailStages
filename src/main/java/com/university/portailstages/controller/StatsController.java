package com.university.portailstages.controller;


import com.university.portailstages.entity.StatutConvention;
import com.university.portailstages.repository.CandidatureRepository;
import com.university.portailstages.repository.ConventionRepository;
import com.university.portailstages.repository.OffreStageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {
    private final CandidatureRepository candidatureRepo;
    private final ConventionRepository conventionRepo;
    private final OffreStageRepository offreRepo;

    // --- Admin stats ---
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String,Object> adminStats() {
        Map<String,Object> map = new HashMap<>();
        map.put("totalCandidatures", candidatureRepo.count());
        map.put("totalConventions", conventionRepo.count());
        map.put("enPreparation", conventionRepo.countByStatut(StatutConvention.EN_PREPARATION));
        map.put("signeeEtudiant", conventionRepo.countByStatut(StatutConvention.SIGNEE_ETUDIANT));
        map.put("signeeEntreprise", conventionRepo.countByStatut(StatutConvention.SIGNEE_ENTREPRISE));
        map.put("validee", conventionRepo.countByStatut(StatutConvention.VALIDEE_ADMIN));
        return map;
    }

    // --- Étudiant stats ---
    @GetMapping("/etudiant")
    @PreAuthorize("hasRole('ETUDIANT')")
    public Map<String,Object> etudiantStats(org.springframework.security.core.Authentication auth) {
        String email = auth.getName();
        Map<String,Object> map = new HashMap<>();
        map.put("mesCandidatures", candidatureRepo.countByEtudiantUserEmail(email));
        map.put("mesConventions", conventionRepo.countByEtudiantEmail(email));
        map.put("conventionsSignee", conventionRepo.countByEtudiantEmailAndStatut(email, StatutConvention.SIGNEE_ETUDIANT));
        map.put("conventionsValidee", conventionRepo.countByEtudiantEmailAndStatut(email, StatutConvention.VALIDEE_ADMIN));
        return map;
    }

    // --- Entreprise stats ---
    @GetMapping("/entreprise")
    @PreAuthorize("hasRole('ENTREPRISE')")
    public Map<String,Object> entrepriseStats(org.springframework.security.core.Authentication auth) {
        String email = auth.getName();
        Map<String,Object> map = new HashMap<>();
        map.put("mesOffres", offreRepo.countByEntrepriseEmail(email));
        map.put("candidaturesRecues", candidatureRepo.countByOffreEntrepriseEmail(email));
        map.put("conventionsSignee", conventionRepo.countByEntrepriseEmailAndStatut(email, StatutConvention.SIGNEE_ENTREPRISE));
        map.put("conventionsValidee", conventionRepo.countByEntrepriseEmailAndStatut(email, StatutConvention.VALIDEE_ADMIN));
        return map;
    }
}
