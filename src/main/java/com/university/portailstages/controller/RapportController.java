package com.university.portailstages.controller;

import com.university.portailstages.dto.AdminReport;
import com.university.portailstages.dto.EntrepriseReport;
import com.university.portailstages.dto.EtudiantReport;
import com.university.portailstages.service.AdminReportService;
import com.university.portailstages.service.EntrepriseReportService;
import com.university.portailstages.service.EtudiantReportService;
import com.university.portailstages.service.PdfConventionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;

@RestController
@RequestMapping("/api/rapports")
@RequiredArgsConstructor
public class RapportController {
    private final PdfConventionService.PdfRapportService pdfService;
    private final AdminReportService adminReportService;
    private final EntrepriseReportService entrepriseReportService;
    private final EtudiantReportService etudiantReportService;

    @GetMapping("/conventions/pdf")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> downloadRapport() throws Exception {
        String path = "rapport_conventions.pdf";
        pdfService.genererRapportConventions(path);
        File file = new File(path);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminReport adminReport() {
        return adminReportService.generate();
    }
    @GetMapping("/entreprise")
    @PreAuthorize("hasRole('ENTREPRISE')")
    public EntrepriseReport entreprise(Authentication auth) {
        return entrepriseReportService.generate(auth.getName());
    }
    @GetMapping("/etudiant")
    @PreAuthorize("hasRole('ETUDIANT')")
    public EtudiantReport etudiant(Authentication auth) {
        return etudiantReportService.generate(auth.getName());
    }
}
