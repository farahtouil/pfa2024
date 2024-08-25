package com.farah.pfa2024.repository;

import com.farah.pfa2024.model.ServiceP;
import com.farah.pfa2024.model.TypeS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicePRepository extends JpaRepository<ServiceP,Long> {
    List<ServiceP> findByType(TypeS type);

}
