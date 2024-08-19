package com.farah.pfa2024.dto;


import com.farah.pfa2024.model.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqResponse {

    private int statusCode;
    private String error;
    private String message;
    private String data;
    private String token;
    private String refreshToken;
    private String exp;
    private Long id_user;
    private String nom;
    private String prenom;
    private String mail;
    private String mdp;
    private String num_tel;
    private String gouvernorat;
    private Date date_de_naissance;
    private Set<ServiceP> servicesP;
    private String image;
    private Role role;
    private List<Utilisateur> utilisateurs;
    private List<ServiceP> servicePs;
    private List<Admin> admins;
    private List<Client> clients;
    private List<Prestataire> prestataires;
    private Admin admin;
    private Client client;
    private Prestataire prestataire;
    private ServiceP serviceP1;


}
