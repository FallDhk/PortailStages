package com.university.portailstages.service;

import com.university.portailstages.entity.Convention;
import com.university.portailstages.entity.SuiviStage;
import com.university.portailstages.entity.User;
import com.university.portailstages.repository.ConventionRepository;
import com.university.portailstages.repository.SuiviStageRepository;
import com.university.portailstages.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SuiviStageService {
    private final SuiviStageRepository repo;
    private final ConventionRepository conventionRepo;
    private final UserRepository userRepo;

    public SuiviStage ajouter(Long conventionId, SuiviStage s, String email) {

        User etudiant = userRepo.findByEmail(email).orElseThrow();
        Convention c = conventionRepo.findById(conventionId).orElseThrow();

        if (!c.getEtudiant().getId().equals(etudiant.getId()))
            throw new RuntimeException("Accès refusé");
        if (s.getProgression() < 0 || s.getProgression() > 100)
            throw new RuntimeException("Progression invalide (0-100)");

        int somme = repo.sumProgression(conventionId);

        if (somme + s.getProgression() > 100)
            throw new RuntimeException(
                    "Progression invalide : " + somme + " + " + s.getProgression() + " > 100%"
            );

        s.setConvention(c);
        s.setDate(LocalDate.now());
        s.setCreeAt(LocalDateTime.now());
        s.setValide(false);

        return repo.save(s);
    }

    public SuiviStage valider(Long id, Map<String, Object> body, String email) {

        User encadrant = userRepo.findByEmail(email).orElseThrow();
        SuiviStage s = repo.findById(id).orElseThrow();

        if (!s.getConvention().getEncadrantPedagogique().getId().equals(encadrant.getId()))
            throw new RuntimeException("Accès refusé");

        Integer prog = (Integer) body.get("progression");

        if (prog != null && (prog < 0 || prog > 100))
            throw new RuntimeException("Progression invalide (0-100)");

        int somme = repo.sumProgression(s.getConvention().getId());

        if (somme + s.getProgression() > 100)
            throw new RuntimeException(
                    "Progression invalide : " + somme + " + " + s.getProgression() + " > 100%"
            );

        s.setCommentaireEncadrant((String) body.get("commentaire"));
//        s.setProgression(prog);
        if (prog != null)
            s.setProgression(prog);
        s.setValide(true);

        return repo.save(s);
    }

    public List<SuiviStage> lister(Long conventionId) {
        return repo.findByConventionId(conventionId);
    }
}
