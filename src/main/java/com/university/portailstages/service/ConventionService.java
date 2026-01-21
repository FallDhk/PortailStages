package com.university.portailstages.service;

import com.university.portailstages.entity.*;
import com.university.portailstages.repository.CandidatureRepository;
import com.university.portailstages.repository.ConventionRepository;
import com.university.portailstages.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConventionService {
    private final ConventionRepository repo;
    private final CandidatureRepository candRepo;
    private final UserRepository userRepo;

    public Convention creerConvention(Long candidatureId) {

        Candidature c = candRepo.findById(candidatureId).orElseThrow();

        if (c.getStatut() != StatutCandidature.ACCEPTEE) {
            throw new ConventionException("Candidature non acceptée");
        }

        Convention conv = new Convention();
        conv.setCandidature(c);
        conv.setEtudiant(c.getEtudiant());
        conv.setEntreprise(c.getOffre().getEntreprise());
        conv.setOffre(c.getOffre());
        conv.setStatut(StatutConvention.EN_PREPARATION);

        return repo.save(conv);
    }

    public List<Convention> mesConventionsEtudiant(String email) {
        User u = userRepo.findByEmail(email).orElseThrow();
        return repo.findByEtudiantId(u.getId());
    }

    public List<Convention> mesConventionsEntreprise(String email) {
        User u = userRepo.findByEmail(email).orElseThrow();
        return repo.findByEntrepriseId(u.getId());
    }

//    public Convention signerEtudiant(Long id) {
//        Convention c = repo.findById(id).orElseThrow();
//
//        if (c.getStatut() != StatutConvention.EN_PREPARATION) {
//            throw new RuntimeException("Convention non signable par l'étudiant");
//        }
//
//        c.setStatut(StatutConvention.SIGNEE_ETUDIANT);
//        c.setSigneEtudiantAt(LocalDateTime.now()); // ← on garde l’historique
//        return repo.save(c);
//    }
@Transactional
public Convention signerEtudiant(Long id, String emailUser) {

    Convention c = getById(id);

    if (!c.getEtudiant().getEmail().equals(emailUser)) {
        throw new ConventionException("Vous n'êtes pas l'étudiant de cette convention");
    }
    if (c.getSigneEtudiantAt() != null) {
        throw new ConventionException("Convention déjà signée par l'étudiant");
    }

    c.setSigneEtudiantAt(LocalDateTime.now());
    c.setStatut(StatutConvention.SIGNEE_ETUDIANT);

    Convention saved = repo.save(c);

    //pdfService.genererPdf(saved);

    return saved;
}

//    public Convention signerEntreprise(Long id) {
//        Convention c = repo.findById(id).orElseThrow();
//
//        if (c.getStatut() != StatutConvention.SIGNEE_ETUDIANT) {
//            throw new RuntimeException("Convention non signable par l'entreprise");
//        }
//
//        c.setStatut(StatutConvention.SIGNEE_ENTREPRISE);
//        c.setSigneEntrepriseAt(LocalDateTime.now());
//        return repo.save(c);
//    }

    @Transactional
    public Convention signerEntreprise(Long id, String emailUser) {

        Convention c = getById(id);
        if (!c.getEntreprise().getEmail().equals(emailUser)) {
            throw new ConventionException("Vous n'êtes pas l'entreprise de cette convention");
        }
        if (c.getSigneEntrepriseAt() != null) {
            throw new ConventionException("Déjà signée par l'entreprise");
        }
        if (c.getStatut() != StatutConvention.SIGNEE_ETUDIANT) {
            throw new ConventionException("Convention pas prête pour signature entreprise");
        }

        c.setSigneEntrepriseAt(LocalDateTime.now());
        c.setStatut(StatutConvention.SIGNEE_ENTREPRISE);

        repo.save(c);

        return c;
    }

    @Transactional
    public Convention valider(Long id) {

        Convention c = getById(id);

        if (c.getValideAdminAt() != null) {
            throw new ConventionException("Déjà validée");
        }
        if (c.getStatut() != StatutConvention.SIGNEE_ENTREPRISE) {
            throw new ConventionException("Convention pas prête pour validation admin");
        }

        c.setValideAdminAt(LocalDateTime.now());
        c.setStatut(StatutConvention.VALIDEE_ADMIN);

        repo.save(c);

        return c;
    }


    public Convention getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ConventionException("Convention introuvable"));
    }

    public class ConventionException extends RuntimeException {
        public ConventionException(String msg) {
            super(msg);
        }
    }
    @RestControllerAdvice
    public class ApiExceptionHandler {

        @ExceptionHandler(ConventionException.class)
        public ResponseEntity<?> handleConvention(ConventionException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("message", ex.getMessage())
            );
        }
    }
}
