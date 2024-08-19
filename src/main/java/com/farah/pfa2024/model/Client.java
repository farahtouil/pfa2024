package com.farah.pfa2024.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("client")
public class Client extends Utilisateur implements Serializable {

    public Client(Long id_user, String nom, String prenom, String num_tel, String gouvernorat, String mail, String mdp) {
        super(id_user, nom, prenom, num_tel, gouvernorat, mail, mdp, Role.client);
    }
}
