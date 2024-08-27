package com.farah.pfa2024.controller;


import com.farah.pfa2024.dto.ReqResponse;
import com.farah.pfa2024.dto.ReservationDTO;
import com.farah.pfa2024.model.Client;
import com.farah.pfa2024.model.Prestataire;
import com.farah.pfa2024.model.Reservation;
import com.farah.pfa2024.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/client/{id_client}")
    public ResponseEntity<ReqResponse> getReservationsByClient(@PathVariable Long id_client) {
        Client client = new Client();  // This should be fetched from the database
        client.setId_user(id_client);
        ReqResponse response = reservationService.getReservationsByClient(client);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /*@GetMapping("/prestataire/{id_pres}")
    public ResponseEntity<ReqResponse> getReservationsByPrestataire(@PathVariable Long id_pres) {
        Prestataire prestataire = new Prestataire();  // This should be fetched from the database
        prestataire.setId_user(id_pres);
        ReqResponse response = reservationService.getReservationsByPrestataire(prestataire);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }*/

    @PostMapping
    public ResponseEntity<ReqResponse> createReservation(@RequestBody ReservationDTO reservation) {
        ReqResponse response = reservationService.createReservation(reservation);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id_res}")
    public ResponseEntity<ReqResponse> deleteReservation(@PathVariable Long id_res) {
        ReqResponse response = reservationService.deleteReservation(id_res);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
