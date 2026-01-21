package com.university.portailstages.service;

import com.university.portailstages.entity.Convention;
import com.university.portailstages.entity.Evaluation;
import com.university.portailstages.entity.Role;
import com.university.portailstages.entity.User;
import com.university.portailstages.repository.ConventionRepository;
import com.university.portailstages.repository.EvaluationRepository;
import com.university.portailstages.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepository repository;
    private final ConventionRepository conventionRepo;
    private final UserRepository userRepo;

    @Transactional
    public Evaluation ajouterEvaluation(Long conventionId, String emailEvaluateur,
                                        int note, String commentaire) {
        Convention c = conventionRepo.findById(conventionId)
                .orElseThrow(() -> new EvaluationException("Convention introuvable"));

        User user = userRepo.findByEmail(emailEvaluateur)
                .orElseThrow(() -> new EvaluationException("Utilisateur introuvable"));

        // Vérifier que l'évaluation est faite après la fin du stage
        if (c.getDateFin().isAfter(LocalDate.now())) {
            throw new EvaluationException("Stage pas encore terminé");
        }

        Evaluation eval = new Evaluation();
        eval.setConvention(c);
        eval.setEvaluateur(user);
        eval.setNote(note);
        eval.setCommentaire(commentaire);
        eval.setDateEvaluation(LocalDateTime.now());

        return repository.save(eval);
    }

    @Transactional(readOnly = true)
    public List<Evaluation> getEvaluationsParConvention(Long conventionId, String emailUser) {
        Convention c = conventionRepo.findById(conventionId)
                .orElseThrow(() -> new EvaluationException("Convention introuvable"));

        // Vérifie que l'utilisateur est autorisé
        boolean isParticipant = c.getEtudiant().getEmail().equals(emailUser) ||
                c.getEntreprise().getEmail().equals(emailUser);

        boolean isAdmin = isAdminUser(emailUser);

        if (!(isParticipant || isAdmin)) {
            throw new EvaluationException("Accès refusé aux évaluations de cette convention");
        }

        return repository.findByConventionId(conventionId);
    }

    // Méthode pour vérifier si l'utilisateur est admin
    private boolean isAdminUser(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new EvaluationException("Utilisateur introuvable"));
        return user.getRole() == Role.ADMIN;
    }


    public class EvaluationException extends RuntimeException {
        public EvaluationException(String msg) {
            super(msg);
        }
    }
    @RestControllerAdvice
    public class ApiExceptionHandler {

        @ExceptionHandler(EvaluationService.EvaluationException.class)
        public ResponseEntity<?> handleConvention(EvaluationService.EvaluationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("message", ex.getMessage())
            );
        }
    }
}
