package com.university.portailstages.controller;

import com.university.portailstages.entity.ProfilEtudiant;
import com.university.portailstages.service.ProfilEtudiantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profil-etudiant")
@RequiredArgsConstructor
public class ProfilEtudiantController {
    private final ProfilEtudiantService service;

    @PostMapping
    @PreAuthorize("hasRole('ETUDIANT')")
    public ProfilEtudiant save(@RequestBody ProfilEtudiant profil,
                               Authentication auth) {
        return service.saveOrUpdate(auth.getName(), profil);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ETUDIANT')")
    public ProfilEtudiant me(Authentication auth) {
        return service.getMyProfil(auth.getName());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<ProfilEtudiant> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProfilEtudiant get(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
