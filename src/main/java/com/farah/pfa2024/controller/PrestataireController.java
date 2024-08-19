package com.farah.pfa2024.controller;

import com.farah.pfa2024.dto.ReqResponse;
import com.farah.pfa2024.model.Prestataire;
import com.farah.pfa2024.model.ServiceP;
import com.farah.pfa2024.service.PrestataireService;
import com.farah.pfa2024.service.ServicePService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
//c'est testé
@RestController
@RequestMapping("/prestataire")
public class PrestataireController {

    @Autowired
    private PrestataireService prestataireService;

    @Autowired
    private ServicePService servicePService;

    @GetMapping
    public ResponseEntity<ReqResponse> getAllPrestataires() {
        return ResponseEntity.ok(prestataireService.getAllPrestataires());
    }

    @GetMapping("/{id_pres}")
    public ResponseEntity<ReqResponse> getPrestataireById(@PathVariable Long id_pres) {
        return ResponseEntity.ok(prestataireService.getPrestataireById(id_pres));
    }

    @PutMapping("/{id_pres}")
    public ResponseEntity<ReqResponse> updatePrestataire(@PathVariable Long id_pres, @RequestBody Prestataire prestataireDet) {
        return ResponseEntity.ok(prestataireService.updatePrestataire(id_pres, prestataireDet));
    }

    @PostMapping("/{id_pres}/services")
    public ResponseEntity<ReqResponse> addServiceToPrestataire(@PathVariable Long id_pres, @RequestBody ServiceP serviceP) {
        ReqResponse response = prestataireService.addServiceToPrestataire(id_pres, serviceP);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/{id_pres}/services/{id_ser}")
    public ResponseEntity<ReqResponse> updateServicePForPrestataire(@PathVariable Long id_pres, @PathVariable Long id_ser, @RequestBody ServiceP serviceP) {
        ReqResponse response = prestataireService.getPrestataireById(id_pres);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id_pres}/services/{id_ser}")
    public ResponseEntity<ReqResponse> deleteServiceFromPrestataire(@PathVariable Long id_pres, @PathVariable Long id_ser) {
        ReqResponse response = prestataireService.deleteServiceFromPrestataire(id_pres, id_ser);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id_pres}")
    public ResponseEntity<ReqResponse> deletePrestataire(@PathVariable Long id_pres) {
        return ResponseEntity.ok(prestataireService.deletePrestataire(id_pres));
    }

    /*@GetMapping
    public List<Prestataire> getAll() {
        return prestataireService.getAllPrestataire();
    }

    @GetMapping("/{id_pres}")
    public Prestataire getPrestataireById(@PathVariable Long id_pres) {
        Optional<Prestataire> prestataire = prestataireService.getPrestataireById(id_pres);
        return prestataire.orElse(null);
    }

    @PostMapping
    public ResponseEntity<Prestataire> createPrestataire(@RequestBody Prestataire prestataire) {
        //prestataire.setServicesP(null);
        //prestataire.setImage(null);
        try {
            if (prestataire.getImage() !=null){
                try {
                    Base64.getDecoder().decode(prestataire.getImage());
                }catch (IllegalArgumentException e){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
            }
            Prestataire createdPrestataire = prestataireService.addPrestataire(prestataire);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPrestataire);
            //return prestataireService.addPrestataire(prestataire);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @PutMapping("/{id_pres}") //modification de tous les champs de plus, possibilité d'ajout d un nouveau service et la modification d'un service existant
    public ResponseEntity<Prestataire> updatePres(@PathVariable Long id_pres, @RequestBody Prestataire presDet) {
        Optional<Prestataire> prestataire = prestataireService.getPrestataireById(id_pres);
        if (prestataire.isPresent()) {
            Prestataire updatePres=prestataire.get();
            updatePres.setNom(presDet.getNom());
            updatePres.setPrenom(presDet.getPrenom());
            //updatePres.setServicesP(presDet.getServicesP());
            updatePres.setImage(presDet.getImage());
            updatePres.setMail(presDet.getMail());
            updatePres.setMdp(presDet.getMdp());
            updatePres.setDate_de_naissance(presDet.getDate_de_naissance());
            updatePres.setGouvernorat(presDet.getGouvernorat());
            updatePres.setNum_tel(presDet.getNum_tel());

            for (ServiceP service : presDet.getServicesP()){
                service.setPrestataire(updatePres);
            }
            updatePres.setServicesP(presDet.getServicesP());

            return ResponseEntity.ok(prestataireService.addPrestataire(updatePres));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id_pres}")
    public ResponseEntity<Void> deletePres(@PathVariable Long id_pres) {
        if (prestataireService.getPrestataireById(id_pres).isPresent()) {
            prestataireService.deletePrestataire(id_pres);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }*/
}
