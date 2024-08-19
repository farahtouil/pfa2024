package com.farah.pfa2024.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("prestataire")
public class Prestataire extends Utilisateur implements Serializable {

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date date_de_naissance;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "prestataire",fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<ServiceP> servicesP;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String image;

    public Prestataire(Long id_user, String nom, String prenom, String num_tel, String gouvernorat, String mail, String mdp, Date date_de_naissance, Set<ServiceP> servicesP, String image) {
        super(id_user, nom, prenom, num_tel, gouvernorat, mail, mdp, Role.prestataire);
        this.date_de_naissance = date_de_naissance;
        this.servicesP = servicesP;
        this.image = image;
    }
}
