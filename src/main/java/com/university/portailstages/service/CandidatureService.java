package com.university.portailstages.service;

import com.university.portailstages.entity.*;
import com.university.portailstages.repository.*;
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
    private final ProfilEtudiantRepository profilRepo;
    private final ConventionRepository conventionRepository;

    public Candidature postuler(Long offreId, String email) {

        ProfilEtudiant profil = profilRepo.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Vous devez créer votre profil avant de postuler"));

        if (!profil.isComplet()) {
            throw new RuntimeException("Profil étudiant incomplet");
        }

        OffreStage offre = offreRepo.findById(offreId).orElseThrow();

        boolean dejaPostule = repo.existsByEtudiantIdAndOffreId(profil.getId(), offreId);
        if (dejaPostule) {
            throw new RuntimeException("Déjà postulé à cette offre");
        }

        Candidature c = new Candidature();
        c.setEtudiant(profil);
        c.setOffre(offre);
        c.setDateCandidature(LocalDate.now());
        c.setStatut(StatutCandidature.EN_ATTENTE);

        return repo.save(c);
    }

    public List<Candidature> mesCandidatures(String email) {
        ProfilEtudiant profil = profilRepo.findByUserEmail(email).orElseThrow();
        return repo.findByEtudiantId(profil.getId());
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

        if (conventionRepository.existsByCandidatureId(c.getId())) return;

        Convention conv = new Convention();
        conv.setCandidature(c);
        conv.setEtudiant(c.getEtudiant().getUser());
        conv.setEntreprise(c.getOffre().getEntreprise());
        conv.setOffre(c.getOffre());
        conv.setDateDebut(c.getOffre().getDateDebut());
        conv.setDateFin(c.getOffre().getDateFin());
        conv.setStatut(StatutConvention.EN_PREPARATION);

        conv.setMissions(c.getOffre().getMissions());
        conv.setCompetences(c.getOffre().getCompetences());
        conv.setFiliere(c.getOffre().getFiliereCible());
        conv.setNiveau(c.getOffre().getNiveauCible());

        conventionRepository.save(conv);
    }
}