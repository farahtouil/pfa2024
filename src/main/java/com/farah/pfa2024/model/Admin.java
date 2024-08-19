package com.farah.pfa2024.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("admin")
public class Admin extends Utilisateur implements Serializable {

    public Admin(Long id_user, String nom, String prenom,String num_tel,String gouvernorat, String mail, String mdp) {
        super(id_user,nom,prenom,null,null,mail,mdp,Role.admin);
    }
}
