package com.university.portailstages.service;

import com.university.portailstages.entity.Convention;
import com.university.portailstages.entity.RapportStage;
import com.university.portailstages.entity.User;
import com.university.portailstages.repository.ConventionRepository;
import com.university.portailstages.repository.RapportStageRepository;
import com.university.portailstages.repository.SuiviStageRepository;
import com.university.portailstages.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RapportStageService {
    private final RapportStageRepository repo;
    private final ConventionRepository conventionRepo;
    private final UserRepository userRepo;
    private final SuiviStageRepository suiviRepo;
    @Transactional
    public RapportStage deposer(Long conventionId,
                                MultipartFile file,
                                String email) throws Exception {

        Convention c = conventionRepo.findById(conventionId).orElseThrow();
        User etudiant = userRepo.findByEmail(email).orElseThrow();

        if (!c.getEtudiant().getId().equals(etudiant.getId()))
            throw new RuntimeException("Accès refusé");

        int progression = suiviRepo.sumProgression(conventionId);

        if (progression < 100)
            throw new RuntimeException("Le suivi doit être à 100%");

        if (repo.existsByConventionId(conventionId))
            throw new RuntimeException("Rapport déjà déposé");

        Path dossier = Paths.get("uploads/rapports");
        Files.createDirectories(dossier);

        String nom = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = dossier.resolve(nom);

        Files.copy(file.getInputStream(), path);

        RapportStage r = new RapportStage();
        r.setConvention(c);
        r.setNomFichier(nom);
        r.setCheminFichier(path.toString());
        r.setDateDepot(LocalDateTime.now());
        r.setValideEncadrant(false);

        return repo.save(r);
    }

    @Transactional
    public RapportStage valider(Long id, String email) {

        RapportStage r = repo.findById(id).orElseThrow();
        User encadrant = userRepo.findByEmail(email).orElseThrow();

        if (!r.getConvention().getEncadrantPedagogique().getId().equals(encadrant.getId()))
            throw new RuntimeException("Accès refusé");

        r.setValideEncadrant(true);
        r.setValideAt(LocalDateTime.now());

        return repo.save(r);
    }


}
