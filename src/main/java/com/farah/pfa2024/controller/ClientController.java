package com.farah.pfa2024.controller;

import com.farah.pfa2024.dto.ReqResponse;
import com.farah.pfa2024.model.Admin;
import com.farah.pfa2024.model.Client;
import com.farah.pfa2024.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//c'est testé
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<ReqResponse> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{id_client}")
    public ResponseEntity<ReqResponse> getClientById(@PathVariable Long id_client) {
        return ResponseEntity.ok(clientService.getClientById(id_client));
    }

    @PutMapping("/{id_client}")
    public ResponseEntity<ReqResponse> updateClient(@PathVariable Long id_client, @RequestBody Client clientDet) {
        return ResponseEntity.ok(clientService.updateClient(id_client, clientDet));
    }

    @DeleteMapping("/{id_client}")
    public ResponseEntity<ReqResponse> deleteClient(@PathVariable Long id_client) {
        return ResponseEntity.ok(clientService.deleteClient(id_client));
    }

    /*@GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }
    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    @GetMapping("/{id_client}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id_client) {
        Optional<Client> client = clientService.getClientById(id_client);
        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id_client}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id_client, @RequestBody Client clientDet) {
        Optional<Client> client = clientService.getClientById(id_client);
        if (client.isPresent()) {
            Client updateClient = client.get();
            updateClient.setNom(clientDet.getNom());
            updateClient.setPrenom(clientDet.getPrenom());
            updateClient.setGouvernorat(clientDet.getGouvernorat());
            updateClient.setMail(clientDet.getMail());
            updateClient.setNum_tel(clientDet.getNum_tel());
            updateClient.setMdp(clientDet.getMdp());
            return ResponseEntity.ok(clientService.addClient(updateClient));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id_client}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id_client) {
        if (clientService.getClientById(id_client).isPresent()) {
            clientService.deleteClient(id_client);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
}
