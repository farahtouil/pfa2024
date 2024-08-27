package com.farah.pfa2024.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceP implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_ser;

    @Enumerated(EnumType.STRING)
    private TypeS type;
    private Double prixparH;

    @ManyToOne
    @JoinColumn(name = "prestataire_id")
    @JsonBackReference
    private Prestataire prestataire;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "service-reservation")
    private List<Reservation> reservations;



}
