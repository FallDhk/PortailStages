package com.university.portailstages.controller;

import com.university.portailstages.entity.Candidature;
import com.university.portailstages.entity.StatutCandidature;
import com.university.portailstages.service.CandidatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidatures")
@RequiredArgsConstructor
public class CandidatureController {

    private final CandidatureService service;

    @PostMapping("/{offreId}")
    @PreAuthorize("hasRole('ETUDIANT')")
    public Candidature postuler(@PathVariable Long offreId,
                                Authentication auth) {
        return service.postuler(offreId, auth.getName());
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ETUDIANT')")
    public List<Candidature> mesCandidatures(Authentication auth) {
        return service.mesCandidatures(auth.getName());
    }

    @GetMapping("/offre/{id}")
    @PreAuthorize("hasRole('ENTREPRISE')")
    public List<Candidature> candidaturesOffre(@PathVariable Long id) {
        return service.candidaturesOffre(id);
    }

    @PutMapping("/{id}/statut/{statut}")
    @PreAuthorize("hasRole('ENTREPRISE')")
    public Candidature changerStatut(@PathVariable Long id,
                                     @PathVariable StatutCandidature statut) {
        return service.changerStatut(id, statut);
    }

    @GetMapping("/offre/{offreId}/me")
    @PreAuthorize("hasRole('ETUDIANT')")
    public ResponseEntity<Candidature> getMyCandidature(@PathVariable Long offreId,
                                                        Authentication auth) {

        String email = auth.getName();
        Optional<Candidature> c = service.getMyCandidature(offreId, email);

        return c.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
