package com.university.portailstages.controller;

import com.university.portailstages.entity.Convention;
import com.university.portailstages.entity.User;
import com.university.portailstages.service.ConventionService;
import com.university.portailstages.service.PdfConventionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/conventions")
@RequiredArgsConstructor
public class ConventionController {
    private final ConventionService service;
    private final PdfConventionService pdfService;

    @PostMapping("/{candidatureId}")
    @PreAuthorize("hasRole('ENTREPRISE')")
    public Convention creer(@PathVariable Long candidatureId) {
        return service.creerConvention(candidatureId);
    }

    @GetMapping("/etudiant")
    @PreAuthorize("hasRole('ETUDIANT')")
    public List<Convention> mesConventionsEtudiant(Authentication auth) {
        return service.mesConventionsEtudiant(auth.getName());
    }

    @GetMapping("/entreprise")
    @PreAuthorize("hasRole('ENTREPRISE')")
    public List<Convention> mesConventionsEntreprise(Authentication auth) {
        return service.mesConventionsEntreprise(auth.getName());
    }

    @GetMapping("/entreprise/stagere")
    @PreAuthorize("hasRole('ENTREPRISE')")
    public List<Convention> mesStagereEntreprise(Authentication auth) {
        return service.mesStagereEntreprise(auth.getName());
    }

    @PostMapping("/{id}/signer-etudiant")
    @PreAuthorize("hasRole('ETUDIANT')")
    public Convention signerEtudiant(@PathVariable Long id,
                                     Authentication auth) {
        return service.signerEtudiant(id, auth.getName());
    }

    @PostMapping("/{id}/signer-entreprise")
    @PreAuthorize("hasRole('ENTREPRISE')")
    public Convention signerEntreprise(@PathVariable Long id,
                                       Authentication auth) {
        return service.signerEntreprise(id, auth.getName());
    }

    @PostMapping("/{id}/valider-admin/{encadrantId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Convention validerAdmin(@PathVariable Long id,@PathVariable Long encadrantId) {
        return service.valider(id,encadrantId);
    }


    @GetMapping("/{id}/pdf")
    public ResponseEntity<Resource> downloadPdf(@PathVariable Long id) throws Exception {

        Convention c = service.getById(id);
        String path = pdfService.genererPdf(c);

        File file = new File(path);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + file.getName())
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{conventionId}/affecter/{encadrantId}")
    public Convention affecterEncadrant(
            @PathVariable Long conventionId,
            @PathVariable Long encadrantId) {
        return service.affecterEncadrant(conventionId, encadrantId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sans-encadrant")
    public List<Convention> conventionsSansEncadrant() {
        return service.conventionsSansEncadrant();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public List<Convention> conventionsAdmin() {
        return service.conventionsAdmin();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getEnseignants")
    public List<User> getEnseignants() {
        return service.getEnsegnant();
    }

    @PutMapping("/{id}/tuteur")
    @PreAuthorize("hasRole('ENTREPRISE')")
    public Convention definirTuteur(@PathVariable Long id,
                                    @RequestBody Map<String, String> body,
                                    Authentication auth) {
        return service.definirTuteurEntreprise(id, body.get("tuteur"), auth.getName());
    }

    @GetMapping("/mes-stages")
    @PreAuthorize("hasRole('ETUDIANT')")
    public List<Convention> mesStages(Authentication auth) {
        Long userId = ((User) auth.getPrincipal()).getId();
        return service.getStagesValides(userId);
    }

    @GetMapping("/encadrant")
    @PreAuthorize("hasRole('ENSEIGNANT')")
    public List<Convention> getByEncadrant(Authentication auth) {

        User encadrant = (User) auth.getPrincipal();

        return service.getByEncadrant(encadrant.getId());
    }

    @GetMapping("/admin/")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Convention> findByStatut(Authentication auth) {

        return service.findByStatut();
    }

    @GetMapping("/ready-soutenance")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Convention> ready() {
        return service.getReadyForSoutenance();
    }


}
