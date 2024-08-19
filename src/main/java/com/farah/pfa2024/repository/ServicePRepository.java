package com.farah.pfa2024.repository;

import com.farah.pfa2024.model.ServiceP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicePRepository extends JpaRepository<ServiceP,Long> {

}
