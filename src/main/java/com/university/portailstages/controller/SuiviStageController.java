package com.university.portailstages.controller;

import com.university.portailstages.entity.SuiviStage;
import com.university.portailstages.service.SuiviStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/suivi")
@RequiredArgsConstructor
public class SuiviStageController {
    private final SuiviStageService service;

    @PostMapping("/{conventionId}")
    @PreAuthorize("hasRole('ETUDIANT')")
    public SuiviStage ajouter(@PathVariable Long conventionId,
                              @RequestBody SuiviStage s,
                              Authentication auth) {
        return service.ajouter(conventionId, s, auth.getName());
    }


    @PutMapping("/{id}/valider")
    @PreAuthorize("hasRole('ENSEIGNANT')")
    public SuiviStage valider(@PathVariable Long id,
                              @RequestBody Map<String, Object> body,
                              Authentication auth) {
        return service.valider(id, body, auth.getName());
    }

    @GetMapping("/convention/{id}")
    public List<SuiviStage> list(@PathVariable Long id) {
        return service.lister(id);
    }

    @GetMapping("/progress/convention/{id}")
    public Integer getProgress(@PathVariable Long id) {
        return service.getConventionProgress(id);
    }
}
