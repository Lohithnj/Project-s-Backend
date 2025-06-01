package com.carpool.CarPoolingSystem.repository;

import com.carpool.CarPoolingSystem.model.VehicleDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleDetailsRepository extends JpaRepository<VehicleDetails, Long> {
    List<VehicleDetails> findByUserEmail(String email);
}

