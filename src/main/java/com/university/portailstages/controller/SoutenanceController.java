package com.university.portailstages.controller;

import com.university.portailstages.entity.Soutenance;
import com.university.portailstages.service.SoutenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conventions")
@RequiredArgsConstructor
public class SoutenanceController {

    private final SoutenanceService service;

    @PostMapping("/{conventionId}/soutenance")
    @PreAuthorize("hasRole('ADMIN')")
    public Soutenance planifier(@PathVariable Long conventionId,
                                @RequestBody Soutenance s) {
        return service.planifier(conventionId, s);
    }

    @PutMapping("/soutenance/{id}/note/{note}")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public Soutenance noter(@PathVariable Long id,
                            @PathVariable Double note) {
        return service.noter(id, note);
    }

    @PutMapping("/soutenance/{id}/valider")
    @PreAuthorize("hasRole('ADMIN')")
    public Soutenance valider(@PathVariable Long id) {
        return service.valider(id);
    }



}
