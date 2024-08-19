package com.farah.pfa2024.controller;

import com.farah.pfa2024.dto.ReqResponse;
import com.farah.pfa2024.model.Prestataire;
import com.farah.pfa2024.model.ServiceP;
import com.farah.pfa2024.service.PrestataireService;
import com.farah.pfa2024.service.ServicePService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//c'est testé
@RestController
@RequestMapping("/service")
public class ServicePController {

    private final ServicePService servicePService;

    @Autowired
    public ServicePController(ServicePService servicePService) {
        this.servicePService = servicePService;
    }

    @GetMapping("/{id_ser}")
    public ResponseEntity<ReqResponse> getServicePById(@PathVariable Long id_ser) {
        ReqResponse response = servicePService.getServicePById(id_ser);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id_ser}")
    public ResponseEntity<ReqResponse> updateService(@PathVariable Long id_ser, @RequestBody Double newPrice) {
        ReqResponse response = servicePService.updateService(id_ser, newPrice);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



   /* @GetMapping("/{id_ser}")
    public ResponseEntity<ServiceP> getServicePById(@PathVariable Long id_ser) {
        Optional<ServiceP> serviceP = servicePService.getServicePById(id_ser);
        return serviceP.isPresent() ? ResponseEntity.ok(serviceP.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id_pres}")
    /*public ResponseEntity<ServiceP> createServiceP(@RequestBody ServiceP serviceP) {
        ServiceP createdServiceP = servicePService.addServiceP(serviceP);
        return ResponseEntity.status(201).body(createdServiceP);
    }
    @Transactional
    public ResponseEntity<Prestataire> addServiceToPrestataire(@PathVariable Long id_pres,@RequestBody Set<ServiceP> servicesP) {
        Optional<Prestataire> prestataire = prestataireService.getPrestataireById(id_pres);
        if (prestataire.isPresent()) {
            Prestataire existingPrestataire = prestataire.get();
            for (ServiceP serviceP : servicesP) {
                serviceP.setPrestataire(existingPrestataire);
                servicePService.addServiceP(serviceP);
            }
            return ResponseEntity.ok(existingPrestataire);
        }else {
            return ResponseEntity.notFound().build();
        }

        */
        /*if (prestataire.isPresent()) {
            serviceP.setPrestataire(prestataire.get());
            ServiceP createdServiceP = servicePService.addServiceP(serviceP);
            return ResponseEntity.status(201).body(createdServiceP);
        } else {
            return ResponseEntity.notFound().build();
        }*/
    }

    //Cette methode ne fonctionne pas!!
    /*@PutMapping("/{id_ser}")
    public ResponseEntity<ServiceP> updateServiceP(@PathVariable Long id_ser, @RequestBody ServiceP servicePDet) {
        Optional<ServiceP> serviceP = servicePService.getServicePById(id_ser);
        if (serviceP.isPresent()) {
            ServiceP updatedServiceP = serviceP.get();
            updatedServiceP.setType(servicePDet.getType());
            updatedServiceP.setPrixparH(servicePDet.getPrixparH());
            return ResponseEntity.ok(updatedServiceP);
        }else {
            return ResponseEntity.notFound().build();
        }

    }*/


