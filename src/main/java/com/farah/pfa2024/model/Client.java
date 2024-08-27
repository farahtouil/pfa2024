package com.farah.pfa2024.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("client")
public class Client extends Utilisateur implements Serializable {

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "client-reservation")
    private List<Reservation> reservations;



    public Client(Long id_user, String nom, String prenom, String num_tel, String gouvernorat, String mail, String mdp) {
        super(id_user, nom, prenom, num_tel, gouvernorat, mail, mdp, Role.client);
    }
}
