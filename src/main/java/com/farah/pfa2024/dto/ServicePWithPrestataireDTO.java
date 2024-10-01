package com.farah.pfa2024.dto;

import com.farah.pfa2024.model.Prestataire;
import com.farah.pfa2024.model.ServiceP;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServicePWithPrestataireDTO {
    private Long id_ser;
    private String type;
    private Double prixparH;
    private PrestataireDTO prestataire;

    // Constructor
    public ServicePWithPrestataireDTO(ServiceP serviceP) {
        this.id_ser = serviceP.getId_ser();
        this.type = serviceP.getType().toString();
        this.prixparH = serviceP.getPrixparH();
        this.prestataire = new PrestataireDTO(serviceP.getPrestataire());
    }


}

