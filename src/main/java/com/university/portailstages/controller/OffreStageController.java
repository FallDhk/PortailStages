package com.university.portailstages.controller;

import com.university.portailstages.entity.OffreStage;
import com.university.portailstages.service.OffreStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/offres")
@RequiredArgsConstructor
public class OffreStageController {

    private final OffreStageService service;

    // Ajouter une offre (ENTREPRISE seulement)
    @PostMapping
    @PreAuthorize("hasRole('ENTREPRISE')")
    public OffreStage create(@RequestBody OffreStage offre, Authentication auth) {
        offre.setEntreprise((com.university.portailstages.entity.User) auth.getPrincipal());
        return service.save(offre);
    }

    // Voir toutes les offres (pagination + tri)
    @GetMapping
    public Page<OffreStage> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateCreation") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return service.getAll(page, size, sortBy, direction);
    }

    //Voir les offres d'une entreprise (pagination + tri)
    @GetMapping("/entreprise")
    @PreAuthorize("hasRole('ENTREPRISE')")
    public Page<OffreStage> getByEntreprise(
            Authentication auth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateCreation") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Long entrepriseId = ((com.university.portailstages.entity.User) auth.getPrincipal()).getId();
        return service.getByEntreprise(entrepriseId, page, size, sortBy, direction);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ENTREPRISE')")
    public OffreStage update(@PathVariable Long id,
                             @RequestBody OffreStage offre) {
        return service.update(id, offre);
    }

    // Supprimer une offre (ENTREPRISE seulement)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ENTREPRISE')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

