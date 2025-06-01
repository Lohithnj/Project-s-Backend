package com.carpool.CarPoolingSystem.repository;


import com.carpool.CarPoolingSystem.model.Fares;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FareRepository extends JpaRepository<Fares, Long> {
   List<Fares> findByRide_RideId(Long rideId);

}
