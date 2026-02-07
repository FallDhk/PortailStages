package com.university.portailstages.service;

import com.university.portailstages.entity.Convention;
import com.university.portailstages.entity.Soutenance;
import com.university.portailstages.entity.StatutConvention;
import com.university.portailstages.repository.ConventionRepository;
import com.university.portailstages.repository.SoutenanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SoutenanceService {

    private final SoutenanceRepository repo;
    private final ConventionRepository conventionRepo;

    @Transactional
    public Soutenance planifier(Long conventionId,
                                Soutenance s) {

        Convention c = conventionRepo.findById(conventionId).orElseThrow();

        if (repo.existsByConventionId(conventionId))
            throw new RuntimeException("Soutenance déjà planifiée");

        s.setConvention(c);
        s.setValide(false);

        return repo.save(s);
    }

    @Transactional
    public Soutenance noter(Long id, Double note) {

        Soutenance s = repo.findById(id).orElseThrow();

        if (note < 0 || note > 20)
            throw new RuntimeException("Note invalide");

        s.setNoteFinale(note);
        return repo.save(s);
    }

    @Transactional
    public Soutenance valider(Long id) {

        Soutenance s = repo.findById(id).orElseThrow();

        s.setValide(true);
        s.setValideAt(LocalDateTime.now());

        Convention c = s.getConvention();
        c.setStatut(StatutConvention.TERMINEE);

        conventionRepo.save(c);

        return repo.save(s);
    }

    public Soutenance getByConvention(Long id) {
        return repo.findByConventionId(id).orElse(null);
    }


}
