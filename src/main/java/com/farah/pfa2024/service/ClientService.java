package com.farah.pfa2024.service;

import com.farah.pfa2024.dto.ReqResponse;
import com.farah.pfa2024.model.Admin;
import com.farah.pfa2024.model.Client;
import com.farah.pfa2024.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ReqResponse getAllClients(){
        ReqResponse reqResponse = new ReqResponse();

        try {
            List<Client> result = clientRepository.findAll();
            if (!result.isEmpty()){
                reqResponse.setClients(result);
                reqResponse.setMessage("Client List");
                reqResponse.setStatusCode(200);
            }else {
                reqResponse.setStatusCode(404);
                reqResponse.setMessage("Pas de clients");
            }
        }catch (Exception e){
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur: "+e.getMessage());
        }
        return reqResponse;
    }

    public ReqResponse getClientById(Long id_client){
        ReqResponse reqResponse = new ReqResponse();

        try {
            Optional<Client> result = clientRepository.findById(id_client);
            if (result.isPresent()) {
                reqResponse.setClient(result.get());
                reqResponse.setMessage("Client trouvé avec id "+id_client);
                reqResponse.setStatusCode(200);
            } else {
                reqResponse.setStatusCode(404);
                reqResponse.setMessage("Client n'existe pas");
            }
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur: " + e.getMessage());
        }

        return reqResponse;
    }

    public ReqResponse deleteClient(Long id_client){
        ReqResponse reqResponse = new ReqResponse();
        try {
            Optional<Client> result = clientRepository.findById(id_client);
            if (result.isPresent()) {
                clientRepository.deleteById(id_client);
                reqResponse.setStatusCode(200);
                reqResponse.setMessage("Client avec l'id"+id_client+"est supprimé avec succés");
            }else {
                reqResponse.setStatusCode(404);
                reqResponse.setMessage("Client n'existe pas");
            }
        }catch (Exception e){
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Erreur lors de la suppression du client: "+e.getMessage());
        }
        return reqResponse;
    }

    public ReqResponse updateClient(Long id_client,Client clientDet) {
        ReqResponse reqResponse = new ReqResponse();
        Optional<Client> client = clientRepository.findById(id_client);
        if (client.isPresent()) {
            Client updateClient = client.get();
            updateClient.setNom(clientDet.getNom());
            updateClient.setPrenom(clientDet.getPrenom());
            updateClient.setMail(clientDet.getMail());
            updateClient.setMdp(clientDet.getMdp());
            updateClient.setGouvernorat(clientDet.getGouvernorat());
            updateClient.setNum_tel(clientDet.getNum_tel());

            clientRepository.save(updateClient);
            reqResponse.setStatusCode(200);
            reqResponse.setClient(updateClient);
            reqResponse.setMessage("Client modifié avec succés");

        } else {
            reqResponse.setStatusCode(404);
            reqResponse.setMessage("Client n'existe pas");

        }
        return reqResponse;
    }

   /* public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id_client) {
        return clientRepository.findById(id_client);
    }

    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Long id_client) {
        clientRepository.deleteById(id_client);
    }*/
}
