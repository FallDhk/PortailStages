package com.university.portailstages.service;

import com.university.portailstages.entity.*;
import com.university.portailstages.repository.CandidatureRepository;
import com.university.portailstages.repository.ConventionRepository;
import com.university.portailstages.repository.OffreStageRepository;
import com.university.portailstages.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidatureService {
    private final CandidatureRepository repo;
    private final OffreStageRepository offreRepo;
    private final UserRepository userRepo;
    private final ConventionRepository conventionRepository;

    public Candidature postuler(Long offreId, String email) {

        User etudiant = userRepo.findByEmail(email).orElseThrow();
        OffreStage offre = offreRepo.findById(offreId).orElseThrow();

        Candidature c = new Candidature();
        c.setEtudiant(etudiant);
        c.setOffre(offre);
        c.setDateCandidature(LocalDate.now());
        c.setStatut(StatutCandidature.EN_ATTENTE);

        return repo.save(c);
    }

    public List<Candidature> mesCandidatures(String email) {
        User etudiant = userRepo.findByEmail(email).orElseThrow();
        return repo.findByEtudiantId(etudiant.getId());
    }

    public List<Candidature> candidaturesOffre(Long offreId) {
        return repo.findByOffreId(offreId);
    }

    @Transactional
    public Candidature changerStatut(Long id, StatutCandidature statut) {
        Candidature c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        c.setStatut(statut);
        Candidature saved = repo.save(c);

        if (statut == StatutCandidature.ACCEPTEE) {
            creerConventionAutomatique(saved);
        }

        return saved;
    }
    private void creerConventionAutomatique(Candidature c) {

        boolean existe = conventionRepository
                .existsByCandidatureId(c.getId());

        if (existe) return;

        Convention conv = new Convention();
        conv.setCandidature(c);
        conv.setEtudiant(c.getEtudiant());
        conv.setEntreprise(c.getOffre().getEntreprise());
        conv.setOffre(c.getOffre());
        conv.setDateDebut(c.getOffre().getDateDebut());
        conv.setDateFin(c.getOffre().getDateFin());
        conv.setStatut(StatutConvention.EN_PREPARATION);

        conventionRepository.save(conv);
    }
}
