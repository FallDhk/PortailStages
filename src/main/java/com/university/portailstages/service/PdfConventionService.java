package com.university.portailstages.service;

import com.university.portailstages.entity.Convention;
import com.university.portailstages.entity.Evaluation;
import com.university.portailstages.repository.ConventionRepository;
import com.university.portailstages.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.openpdf.text.Document;
import org.openpdf.text.Font;
import org.openpdf.text.PageSize;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


@Service
public class PdfConventionService {
    public String genererPdf(Convention c) {
        try {
            File dir = new File("pdf");
            if (!dir.exists()) dir.mkdirs();

            String path = "pdf/convention_" + c.getId() + ".pdf";

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(path));

            document.open();

            Font title = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 12);

            document.add(new Paragraph("CONVENTION DE STAGE", title));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Etudiant : " + c.getEtudiant().getEmail(), normal));
            document.add(new Paragraph("Entreprise : " + c.getEntreprise().getEmail(), normal));
            document.add(new Paragraph("Offre : " + c.getOffre().getTitre(), normal));
            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Période : du " + c.getDateDebut() + " au " + c.getDateFin(), normal
            ));
            document.add(new Paragraph("Statut : " + c.getStatut(), normal));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("SIGNATURES", title));

            document.add(new Paragraph("Etudiant : " + c.getSigneEtudiantAt(), normal));

            document.add(new Paragraph("Entreprise : " + c.getSigneEntrepriseAt(), normal));
            document.add(new Paragraph("Admin : " + c.getValideAdminAt(), normal));

            document.close();

            return path;
        } catch (Exception e) {
            throw new RuntimeException("Erreur génération PDF", e);
        }
    }

    @Service
    @RequiredArgsConstructor
    public class PdfRapportService {

        private final ConventionRepository conventionRepo;
        private final EvaluationRepository evaluationRepo;

        public String genererRapportConventions(String path) throws Exception {

            List<Convention> conventions = conventionRepo.findAll();

            // Génération PDF OpenPDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            document.add(new Paragraph("Rapport des conventions de stage"));
            document.add(new Paragraph(" "));

            for (Convention c : conventions) {
                document.add(new Paragraph("Convention ID: " + c.getId()));
                document.add(new Paragraph("Etudiant: " + c.getEtudiant().getNom()));
                document.add(new Paragraph("Entreprise: " + c.getEntreprise().getNom()));
                document.add(new Paragraph("Offre: " + c.getOffre().getTitre()));
                document.add(new Paragraph("Statut: " + c.getStatut()));
                document.add(new Paragraph("Date début: " + c.getDateDebut()));
                document.add(new Paragraph("Date fin: " + c.getDateFin()));

                List<Evaluation> evals = evaluationRepo.findByConventionId(c.getId());
                for (Evaluation e : evals) {
                    document.add(new Paragraph(" - Evaluation par " + e.getEvaluateur().getNom() +
                            " Note: " + e.getNote() +
                            " Commentaire: " + e.getCommentaire()));
                }

                document.add(new Paragraph(" "));
            }

            document.close();
            return path;
        }
    }

}
