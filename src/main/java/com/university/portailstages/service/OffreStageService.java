package com.university.portailstages.service;

import com.university.portailstages.entity.OffreStage;
import com.university.portailstages.repository.OffreStageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class OffreStageService {

    private final OffreStageRepository repository;

    public OffreStage save(OffreStage offre) {
        return repository.save(offre);
    }

    // Pagination + tri
    public Page<OffreStage> getAll(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<OffreStage> result = repository.findAll(pageable);

        if (page >= result.getTotalPages() && result.getTotalPages() > 0) {
            throw new RuntimeException("Numéro de page invalide");
        }

        return result;
    }

    public OffreStage getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));
    }

    //Pagination + tri entreprise
    public Page<OffreStage> getByEntreprise(Long entrepriseId, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repository.findByEntrepriseId(entrepriseId, pageable);
    }

    public OffreStage update(Long id, OffreStage offre) {
        OffreStage existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        existing.setTitre(offre.getTitre());
        existing.setDescription(offre.getDescription());
        existing.setLieu(offre.getLieu());
        existing.setCompetences(offre.getCompetences());

        existing.setDateDebut(offre.getDateDebut());
        existing.setDateFin(offre.getDateFin());
        existing.setFiliereCible(offre.getFiliereCible());
        existing.setMissions(offre.getMissions());
        existing.setNiveauCible(offre.getNiveauCible());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
