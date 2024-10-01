package com.farah.pfa2024.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_res;

    @ManyToOne
    @JoinColumn(name = "id_client")
    @JsonBackReference(value = "client-reservation")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_ser")
    @JsonBackReference(value = "service-reservation")
    private ServiceP service;

    @Enumerated(EnumType.STRING)
    private Stat statut;

    private Time heure_dep;
    private Time heure_fin;

    private Double prix;

    @Temporal(TemporalType.DATE)
    private Date date_unique;
}
