package com.farah.pfa2024.service;

import com.farah.pfa2024.dto.ReqResponse;
import com.farah.pfa2024.model.ServiceP;
import com.farah.pfa2024.repository.ServicePRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


//the role of this service is to get a single service by id and to add a single service to an existing prestataire
@Service
public class ServicePService {

    private final ServicePRepository servicePRepository;

    @Autowired
    public ServicePService(ServicePRepository servicePRepository) {
        this.servicePRepository = servicePRepository;
    }

    public ReqResponse getAllServices() {
        ReqResponse reqResponse = new ReqResponse();
        try {
            List<ServiceP> services = servicePRepository.findAll();
            if (!services.isEmpty()) {
                reqResponse.setStatusCode(200);
                reqResponse.setMessage("Tous les services récupérés avec succès");
                reqResponse.setServicePs(services);
            } else {
                reqResponse.setStatusCode(204);  // No Content
                reqResponse.setMessage("Aucun service trouvé");
            }
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur lors de la récupération des services: " + e.getMessage());
        }
        return reqResponse;
    }


    public ReqResponse getServicePById(Long id_ser) {
        ReqResponse reqResponse = new ReqResponse();

        try {
            Optional<ServiceP> result = servicePRepository.findById(id_ser);
            if (result.isPresent()) {
                reqResponse.setServicePs(List.of(result.get()));
                reqResponse.setMessage("Service trouvé avec id " + id_ser);
                reqResponse.setStatusCode(200);
            } else {
                reqResponse.setStatusCode(404);
                reqResponse.setMessage("Service n'existe pas");
            }
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur: " + e.getMessage());
        }

        return reqResponse;
    }

    public ReqResponse updateService(Long id_ser, Double newPrice) {
        ReqResponse reqResponse = new ReqResponse();
        try {
            Optional<ServiceP> servicePOpt = servicePRepository.findById(id_ser);
            if (servicePOpt.isPresent()) {
                ServiceP serviceP = servicePOpt.get();

                serviceP.setPrixparH(newPrice);
                servicePRepository.save(serviceP);

                reqResponse.setStatusCode(200);
                reqResponse.setMessage("Service mis à jour avec succès");
                reqResponse.setServicePs(Collections.singletonList(serviceP));
            } else {
                reqResponse.setStatusCode(404);
                reqResponse.setMessage("Service n'existe pas");
            }
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur lors de la mise à jour du service: " + e.getMessage());
        }
        return reqResponse;
    }

    /*public ReqResponse deleteServiceP(Long id_ser) {
        ReqResponse reqResponse = new ReqResponse();
        try {
            if (servicePRepository.existsById(id_ser)) {
                servicePRepository.deleteById(id_ser);
                reqResponse.setStatusCode(200);
                reqResponse.setMessage("Service supprimé avec succès");
            } else {
                reqResponse.setStatusCode(404);
                reqResponse.setMessage("Service n'existe pas");
            }
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur lors de la suppression du service: " + e.getMessage());
        }
        return reqResponse;
    }*/



   /* public ReqResponse updateServicePForPrestataire(Prestataire prestataire) {
        ReqResponse reqResponse = new ReqResponse();

        try {
            Set<ServiceP> servicesP = prestataire.getServicesP();
            servicePRepository.saveAll(servicesP);
            reqResponse.setServicesP(servicesP);
            reqResponse.setMessage("Services du prestataire mis à jour avec succès");
            reqResponse.setStatusCode(200);
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur lors de la mise à jour des services du prestataire: " + e.getMessage());
        }

        return reqResponse;
    }*/

   /* public List<ServiceP> getAllServiceP() {
        return servicePRepository.findAll();
    }

    public Optional<ServiceP> getServicePById(Long id_ser) {
        return servicePRepository.findById(id_ser);
    }

    public ServiceP addServiceP(ServiceP serviceP) {
        return servicePRepository.save(serviceP);
    }

    public void deleteServiceP(Long id_ser) {
        servicePRepository.deleteById(id_ser);
    }

    public void updateServicePForPrestataire(Prestataire prestataire) {
        Set<ServiceP> servicesP = prestataire.getServicesP();
        servicePRepository.saveAll(servicesP);
    }*/
}
