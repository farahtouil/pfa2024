package com.farah.pfa2024.dto;

import com.farah.pfa2024.model.Client;
import com.farah.pfa2024.model.ServiceP;
import com.farah.pfa2024.model.Stat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationDTO {

    private Client client;
    private ServiceP service;
    private Stat statut;
    private Time heure_dep;
    private Time heure_fin;
    private Double prix;
    private Date date_unique;


}
