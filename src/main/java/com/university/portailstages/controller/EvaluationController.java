package com.university.portailstages.controller;

import com.university.portailstages.dto.EvaluationRequest;
import com.university.portailstages.entity.Evaluation;
import com.university.portailstages.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
@RequiredArgsConstructor
public class EvaluationController {
    private final EvaluationService service;

    @PostMapping("/{conventionId}")
    @PreAuthorize("hasRole('ENTREPRISE')")
    public Evaluation ajouterEvaluation(@PathVariable Long conventionId,
                                        @RequestBody EvaluationRequest dto,
                                        Authentication auth) {
        return service.ajouterEvaluation(conventionId, auth.getName(), dto.getNote(), dto.getCommentaire());
    }

        @GetMapping("/convention/{id}")
        @PreAuthorize("hasRole('ADMIN') or hasRole('ETUDIANT') or hasRole('ENTREPRISE')")
        public List<Evaluation> getEvaluations(@PathVariable Long id, Authentication auth) {
            String emailUser = auth.getName();
            return service.getEvaluationsParConvention(id, emailUser);
        }
}
