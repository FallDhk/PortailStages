package com.university.portailstages.controller;

import com.university.portailstages.entity.RapportStage;
import com.university.portailstages.service.RapportStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/rapport")
@RequiredArgsConstructor
public class RapportStageController {
    private final RapportStageService service;
    @PostMapping("/{conventionId}/deposer")
    @PreAuthorize("hasRole('ETUDIANT')")
    public RapportStage deposer(@PathVariable Long conventionId,
                                @RequestParam MultipartFile file,
                                Authentication auth) throws Exception {
        return service.deposer(conventionId, file, auth.getName());
    }
    @PutMapping("/{id}/valider")
    @PreAuthorize("hasRole('ENSEIGNANT')")
    public RapportStage valider(@PathVariable Long id,
                                Authentication auth) {
        return service.valider(id, auth.getName());
    }

}
