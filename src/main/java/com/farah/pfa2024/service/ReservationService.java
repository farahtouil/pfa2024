package com.farah.pfa2024.service;

import com.farah.pfa2024.dto.ReqResponse;
import com.farah.pfa2024.dto.ReservationDTO;
import com.farah.pfa2024.model.*;
import com.farah.pfa2024.repository.ClientRepository;
import com.farah.pfa2024.repository.ReservationRepository;
import com.farah.pfa2024.repository.ServicePRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final ServicePRepository servicePRepository;
    private java.util.stream.Collectors Collectors;

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
                //reqResponse.setReservations(reservations);  // Set list of reservations in the response

                List<ReservationDTO> reservationDTOs = reservations.stream().map(reservation -> {
                    ReservationDTO dto = new ReservationDTO();
                    dto.setStatut(reservation.getStatut());
                    dto.setHeure_dep(reservation.getHeure_dep());
                    dto.setHeure_fin(reservation.getHeure_fin());
                    dto.setPrix(reservation.getPrix());
                    dto.setDate_unique(reservation.getDate_unique());
                    dto.setService(reservation.getService());
                    dto.setId_res(reservation.getId_res());

                    if (reservation.getClient() != null) {
                        dto.setClient(reservation.getClient());
                        dto.setId_client(reservation.getClient().getId_user()); // Set the client ID
                    }

                    return dto;
                }).collect(Collectors.toList());
                reqResponse.setReservationDTOs(reservationDTOs);

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
    public ReqResponse getReservationsByPrestataire(Prestataire prestataire) {
        ReqResponse reqResponse = new ReqResponse();
        try {
            List<Reservation> reservations = reservationRepository.findByServicePrestataire(prestataire);
            if (!reservations.isEmpty()) {
                reqResponse.setStatusCode(200);
                reqResponse.setMessage("Reservations for prestataire retrieved successfully");
                //reqResponse.setReservations(reservations);  // Set list of reservations in the response

                List<ReservationDTO> reservationDTOs = reservations.stream().map(reservation -> {
                    ReservationDTO dto = new ReservationDTO();
                    dto.setStatut(reservation.getStatut());
                    dto.setHeure_dep(reservation.getHeure_dep());
                    dto.setHeure_fin(reservation.getHeure_fin());
                    dto.setPrix(reservation.getPrix());
                    dto.setDate_unique(reservation.getDate_unique());
                    dto.setService(reservation.getService());
                    dto.setId_res(reservation.getId_res());

                    if (reservation.getClient() != null) {
                        dto.setClient(reservation.getClient());
                        dto.setId_client(reservation.getClient().getId_user()); // Set the client ID
                    }

                    return dto;
                }).collect(Collectors.toList());
                reqResponse.setReservationDTOs(reservationDTOs);

            } else {
                reqResponse.setStatusCode(204);  // No Content
                reqResponse.setMessage("No reservations found for the prestataire");
            }
        } catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Error retrieving reservations for prestataire: " + e.getMessage());
        }
        return reqResponse;
    }

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

    //Accept or reject a reservation
    public ReqResponse updateReservation(Long id_res, ReservationDTO reservationDTO) {
        ReqResponse reqResponse = new ReqResponse();
        try {
            Reservation reservation = reservationRepository.findById(id_res)
                    .orElseThrow(() -> new RuntimeException("Reservation not found"));

            if (reservation.getStatut()== Stat.en_attente) {
                if (reservationDTO.getStatut()== null) {
                    reqResponse.setStatusCode(400);
                    reqResponse.setMessage("Statut is null");
                    return reqResponse;
                }

                switch (reservationDTO.getStatut()) {
                    case confirmee:
                        reservation.setStatut(Stat.confirmee);
                        break;
                    case refusee:
                        reservation.setStatut(Stat.refusee);
                        break;
                    default:
                        reqResponse.setStatusCode(400);  // Bad Request
                        reqResponse.setMessage("Invalid status. Please use 'confirmee' or 'refusee'.");
                        return reqResponse;
                }

                Reservation savedReservation = reservationRepository.save(reservation);

                reqResponse.setStatusCode(200);
                reqResponse.setMessage("Reservation status updated successfully");
                reqResponse.setReservation(savedReservation);
            }else {
                reqResponse.setStatusCode(400);
                reqResponse.setMessage("Reservation status can only be updated if it is 'en attente'.");
            }
        }catch (Exception e) {
            reqResponse.setStatusCode(500);
            reqResponse.setMessage("Error updating reservation: " + e.getMessage());
        }
        return reqResponse;
    }
}
