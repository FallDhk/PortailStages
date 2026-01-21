package com.university.portailstages.controller;

import com.university.portailstages.entity.Convention;
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

    @PostMapping("/{id}/valider-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Convention validerAdmin(@PathVariable Long id) {
        return service.valider(id);
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


}
