package com.university.portailstages.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToOne;
import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class ProfilEtudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    private String prenom;
    private String telephone;
    private String adresse;
    private String matricule;
    private String filiere;
    private String niveau;
    private String anneeUniversitaire;
    private String etablissement;
    @Column(length = 2000)
    private String competences;

    private boolean complet = false;

}
