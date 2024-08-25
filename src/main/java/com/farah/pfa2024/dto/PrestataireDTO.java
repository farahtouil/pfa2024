package com.farah.pfa2024.dto;

import com.farah.pfa2024.model.Prestataire;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrestataireDTO {
    private Long id_user;
    private String nom;
    private String prenom;
    private String num_tel;
    private String gouvernorat;
    private String mail;
    private String date_de_naissance;
    private String image;

    // Constructor
    public PrestataireDTO(Prestataire prestataire) {
        this.id_user = prestataire.getId_user();
        this.nom = prestataire.getNom();
        this.prenom = prestataire.getPrenom();
        this.num_tel = prestataire.getNum_tel();
        this.gouvernorat = prestataire.getGouvernorat();
        this.mail = prestataire.getMail();
        this.date_de_naissance = String.valueOf(prestataire.getDate_de_naissance());
        this.image = prestataire.getImage();
    }

    // Getters and Setters
    // ...
}
