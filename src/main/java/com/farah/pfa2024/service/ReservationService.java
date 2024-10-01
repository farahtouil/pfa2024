package com.farah.pfa2024.service;

import com.farah.pfa2024.dto.ReqResponse;
import com.farah.pfa2024.dto.ReservationDTO;
import com.farah.pfa2024.model.Client;
import com.farah.pfa2024.model.Prestataire;
import com.farah.pfa2024.model.Reservation;
import com.farah.pfa2024.model.ServiceP;
import com.farah.pfa2024.repository.ClientRepository;
import com.farah.pfa2024.repository.ReservationRepository;
import com.farah.pfa2024.repository.ServicePRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final ServicePRepository servicePRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ClientRepository clientRepository, ServicePRepository servicePRepository) {
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
        this.servicePRepository = servicePRepository;
    }

    // Fetch all reservations for a specific client
    public ReqResponse getReservationsByClient(Client client) {
        ReqResponse reqResponse = new ReqResponse();
        try {
            List<Reservation> reservations = reservationRepository.findByClient(client);
            if (!reservations.isEmpty()) {
                reqResponse.setStatusCode(200);
                reqResponse.setMessage("Reservations for client retrieved successfully");
                reqResponse.setReservations(reservations);  // Set list of reservations in the response
            } else {
                reqResponse.setStatusCode(204);  // No Content
                reqResponse.setMessage("No reservations found for the client");
            }
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Error retrieving reservations for client: " + e.getMessage());
        }
        return reqResponse;
    }

    // Fetch all reservations for a specific prestataire
    /*public ReqResponse getReservationsByPrestataire(Prestataire prestataire) {
        ReqResponse reqResponse = new ReqResponse();
        try {
            List<Reservation> reservations = reservationRepository.findByPrestataire(prestataire);
            if (!reservations.isEmpty()) {
                reqResponse.setStatusCode(200);
                reqResponse.setMessage("Reservations for prestataire retrieved successfully");
                reqResponse.setReservations(reservations);  // Set list of reservations in the response
            } else {
                reqResponse.setStatusCode(204);  // No Content
                reqResponse.setMessage("No reservations found for the prestataire");
            }
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Error retrieving reservations for prestataire: " + e.getMessage());
        }
        return reqResponse;
    }*/

    // Create a new reservation
    public ReqResponse createReservation(ReservationDTO reservationDTO) {
        ReqResponse reqResponse = new ReqResponse();
        try {
            Client client = clientRepository.findById(reservationDTO.getClient().getId_user())
                    .orElseThrow(() -> new RuntimeException("Client not found"));
            ServiceP serviceP = servicePRepository.findById(reservationDTO.getService().getId_ser())
                    .orElseThrow(() -> new RuntimeException("Service not found"));

            int heureDep = reservationDTO.getHeure_dep().toLocalTime().getHour();
            int heureFin = reservationDTO.getHeure_fin().toLocalTime().getHour();

            int durationService = heureFin - heureDep;


            double prixTot = durationService * serviceP.getPrixparH();

            if (durationService <= 0) {
                throw new RuntimeException("Invalid time range: heure_fin should be greater than heure_dep");
            }


            Reservation reservation = new Reservation();
            reservation.setClient(client);
            reservation.setService(serviceP);
            reservation.setStatut(reservationDTO.getStatut());
            reservation.setHeure_dep(reservationDTO.getHeure_dep());
            reservation.setHeure_fin(reservationDTO.getHeure_fin());
            reservation.setPrix(prixTot);
            reservation.setDate_unique(reservationDTO.getDate_unique());

            Reservation savedReservation = reservationRepository.save(reservation);
            reqResponse.setStatusCode(201);
            reqResponse.setMessage("Reservation created successfully");
            reqResponse.setReservation(savedReservation);
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Error creating reservation: " + e.getMessage());
        }
        return reqResponse;
    }


    // Delete a reservation by its ID
    public ReqResponse deleteReservation(Long id_res) {
        ReqResponse reqResponse = new ReqResponse();
        try {
            if (reservationRepository.existsById(id_res)) {
                reservationRepository.deleteById(id_res);
                reqResponse.setStatusCode(200);
                reqResponse.setMessage("Reservation deleted successfully");
            } else {
                reqResponse.setStatusCode(404);
                reqResponse.setMessage("Reservation does not exist");
            }
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Error deleting reservation: " + e.getMessage());
        }
        return reqResponse;
    }
}
