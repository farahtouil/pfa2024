package com.farah.pfa2024.service;

import com.farah.pfa2024.dto.ReqResponse;
import com.farah.pfa2024.dto.ServicePWithPrestataireDTO;
import com.farah.pfa2024.model.Prestataire;
import com.farah.pfa2024.model.ServiceP;
import com.farah.pfa2024.repository.PrestataireRepository;
import com.farah.pfa2024.repository.ServicePRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PrestataireService {

    @Autowired
    private PrestataireRepository prestataireRepository;
    @Autowired
    private ServicePRepository servicePRepository;

    public ReqResponse getAllPrestataires() {
        ReqResponse reqResponse = new ReqResponse();

        try {
            List<Prestataire> result = prestataireRepository.findAll();
            if (!result.isEmpty()) {
                reqResponse.setPrestataires(result);
                reqResponse.setMessage("Prestataire List");
                reqResponse.setStatusCode(200);
            } else {
                reqResponse.setStatusCode(404);
                reqResponse.setMessage("Pas de prestataires");
            }
        }catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur: " + e.getMessage());
        }
        return reqResponse;
    }

    public ReqResponse getPrestataireById(Long id_prestataire) {
        ReqResponse reqResponse = new ReqResponse();

        try {
            Optional<Prestataire> result = prestataireRepository.findById(id_prestataire);
            if (result.isPresent()) {
                reqResponse.setPrestataire(result.get());
                reqResponse.setMessage("Prestataire trouvé avec id " + id_prestataire);
                reqResponse.setStatusCode(200);
            } else {
                reqResponse.setStatusCode(404);
                reqResponse.setMessage("Prestataire n'existe pas");
            }
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur: " + e.getMessage());
        }

        return reqResponse;
    }

    public ReqResponse deletePrestataire(Long id_prestataire) {
        ReqResponse reqResponse = new ReqResponse();
        try {
            Optional<Prestataire> result = prestataireRepository.findById(id_prestataire);
            if (result.isPresent()) {
                prestataireRepository.deleteById(id_prestataire);
                reqResponse.setStatusCode(200);
                reqResponse.setMessage("Prestataire avec l'id " + id_prestataire + " est supprimé avec succès");
            } else {
                reqResponse.setStatusCode(404);
                reqResponse.setMessage("Prestataire n'existe pas");
            }
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur lors de la suppression du prestataire: " + e.getMessage());
        }
        return reqResponse;
    }

    public ReqResponse updatePrestataire(Long id_prestataire, Prestataire prestataireDet) {
        ReqResponse reqResponse = new ReqResponse();
        Optional<Prestataire> prestataire = prestataireRepository.findById(id_prestataire);
        if (prestataire.isPresent()) {
            Prestataire updatePrestataire = prestataire.get();
            updatePrestataire.setNom(prestataireDet.getNom());
            updatePrestataire.setPrenom(prestataireDet.getPrenom());
            updatePrestataire.setMail(prestataireDet.getMail());
            updatePrestataire.setMdp(prestataireDet.getMdp());
            updatePrestataire.setDate_de_naissance(prestataireDet.getDate_de_naissance());
            updatePrestataire.setImage(prestataireDet.getImage());
            updatePrestataire.setNum_tel(prestataireDet.getNum_tel());
            updatePrestataire.setGouvernorat(prestataireDet.getGouvernorat());

            prestataireRepository.save(updatePrestataire);
            reqResponse.setStatusCode(200);
            reqResponse.setPrestataire(updatePrestataire);
            reqResponse.setMessage("Prestataire modifié avec succès");
        } else {
            reqResponse.setStatusCode(404);
            reqResponse.setMessage("Prestataire n'existe pas");
        }
        return reqResponse;
    }

    public ReqResponse addServiceToPrestataire(Long id_pres, ServiceP serviceP) {
        ReqResponse reqResponse = new ReqResponse();
        try {
            Optional<Prestataire> prestataireOpt = prestataireRepository.findById(id_pres);
            if (prestataireOpt.isPresent()) {
                Prestataire prestataire = prestataireOpt.get();
                serviceP.setPrestataire(prestataire);    // Link service to the prestataire
                servicePRepository.save(serviceP);

                Set<ServiceP> servicesP = prestataire.getServicesP();
                if (servicesP == null) {
                    servicesP = new HashSet<>();
                }
                servicesP.add(serviceP);
                prestataire.setServicesP(servicesP);
                prestataireRepository.save(prestataire);

                // Convert services to DTO
                List<ServicePWithPrestataireDTO> serviceDTOs = servicesP.stream()
                        .map(ServicePWithPrestataireDTO::new)
                        .collect(Collectors.toList());

                reqResponse.setStatusCode(201);
                reqResponse.setMessage("Service ajouté au prestataire avec succès");
                reqResponse.setServicePs(serviceDTOs);  // Use DTO
            } else {
                reqResponse.setStatusCode(404);
                reqResponse.setMessage("Prestataire n'existe pas");
            }
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur lors de l'ajout du service au prestataire: " + e.getMessage());
        }
        return reqResponse;
    }


    public ReqResponse updateServicePForPrestataire(Long id_pres, ServiceP serviceP) {
        ReqResponse reqResponse = new ReqResponse();

        try {
            Optional<Prestataire> prestataireOpt = prestataireRepository.findById(id_pres);
            if (prestataireOpt.isPresent()) {
                Optional<ServiceP> existingServiceOpt = servicePRepository.findById(serviceP.getId_ser());
                if (existingServiceOpt.isPresent()) {
                    ServiceP existingService = existingServiceOpt.get();
                    existingService.setType(serviceP.getType());
                    existingService.setPrixparH(serviceP.getPrixparH());
                    servicePRepository.save(existingService);
                    reqResponse.setStatusCode(200);
                    reqResponse.setMessage("Service mis à jour avec succès");
                    reqResponse.setServicesP((Set<ServiceP>) existingService);
                } else {
                    reqResponse.setStatusCode(404);
                    reqResponse.setMessage("Service n'existe pas");
                }
            } else {
                reqResponse.setStatusCode(404);
                reqResponse.setMessage("Prestataire n'existe pas");
            }
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur lors de la mise à jour du service: " + e.getMessage());
        }

        return reqResponse;
    }

    public ReqResponse deleteServiceFromPrestataire(Long id_pres, Long id_ser) {
        ReqResponse reqResponse = new ReqResponse();
        try {
            Optional<Prestataire> prestataireOpt = prestataireRepository.findById(id_pres);
            if (prestataireOpt.isPresent()) {
                Optional<ServiceP> servicePOpt = servicePRepository.findById(id_ser);
                if (servicePOpt.isPresent()) {
                    ServiceP serviceP = servicePOpt.get();
                    Prestataire prestataire = prestataireOpt.get();
                    Set<ServiceP> servicesP = prestataire.getServicesP();
                    if (servicesP != null && servicesP.remove(serviceP)) {
                        servicePRepository.deleteById(id_ser);
                        prestataire.setServicesP(servicesP);
                        prestataireRepository.save(prestataire);
                        reqResponse.setStatusCode(200);
                        reqResponse.setMessage("Service supprimé du prestataire avec succès");
                    } else {
                        reqResponse.setStatusCode(404);
                        reqResponse.setMessage("Service non associé au prestataire");
                    }
                } else {
                    reqResponse.setStatusCode(404);
                    reqResponse.setMessage("Service n'existe pas");
                }
            } else {
                reqResponse.setStatusCode(404);
                reqResponse.setMessage("Prestataire n'existe pas");
            }
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur lors de la suppression du service du prestataire: " + e.getMessage());
        }
        return reqResponse;
    }

   /* public List<Prestataire> getAllPrestataire() {
        return prestataireRepository.findAll();
    }

    public Optional<Prestataire> getPrestataireById(Long id_pres) {
        return prestataireRepository.findById(id_pres);
    }

    public Prestataire addPrestataire(Prestataire prestataire) {
        return prestataireRepository.save(prestataire);
    }

    public void deletePrestataire(Long id_pres) {
        prestataireRepository.deleteById(id_pres);
    }*/
}
