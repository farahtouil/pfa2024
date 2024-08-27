package com.farah.pfa2024.repository;

import com.farah.pfa2024.model.Client;
import com.farah.pfa2024.model.Prestataire;
import com.farah.pfa2024.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClient(Client client);
    /*List<Reservation> findByPrestataire(Prestataire prestataire);*/

}
