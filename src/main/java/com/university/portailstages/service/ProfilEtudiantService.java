package com.university.portailstages.service;

import com.university.portailstages.entity.ProfilEtudiant;
import com.university.portailstages.entity.Role;
import com.university.portailstages.entity.User;
import com.university.portailstages.repository.ProfilEtudiantRepository;
import com.university.portailstages.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfilEtudiantService {
    private final ProfilEtudiantRepository repo;
    private final UserRepository userRepo;

    // CREATE or UPDATE
    @Transactional
    public ProfilEtudiant saveOrUpdate(String email, ProfilEtudiant profilDto) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérifier si un profil existe déjà
        if (user.getProfilEtudiant() != null) {
            throw new RuntimeException("Profil déjà créé");
        }

        ProfilEtudiant profil = repo.findByUserEmail(email)
                .orElse(new ProfilEtudiant());
        profil.setUser(user);
        profil.setPrenom(profilDto.getPrenom());
        profil.setTelephone(profilDto.getTelephone());
        profil.setAdresse(profilDto.getAdresse());
        profil.setMatricule(profilDto.getMatricule());
        profil.setFiliere(profilDto.getFiliere());
        profil.setNiveau(profilDto.getNiveau());
        profil.setAnneeUniversitaire(profilDto.getAnneeUniversitaire());
        profil.setEtablissement(profilDto.getEtablissement());
        profil.setCompetences(profilDto.getCompetences());

        profil.setComplet(true);

        ProfilEtudiant saved = repo.save(profil);

        // mettre à jour le user
        user.setProfilEtudiant(saved);
        userRepo.save(user);

        return saved;
    }


    // MON PROFILE
    public ProfilEtudiant getMyProfil(String email) {
        return repo.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Profil non créé"));
    }

    // ADMIN : LIST ALL

    public List<ProfilEtudiant> getAll() {
        return repo.findAll();
    }

    // ADMIN : GET BY ID
    public ProfilEtudiant getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Profil introuvable"));
    }

    // DELETE
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
