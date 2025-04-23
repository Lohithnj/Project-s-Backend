package com.carpool.CarPoolingSystem.repository;

import com.carpool.CarPoolingSystem.model.RideStopover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideStopoverRepository extends JpaRepository<RideStopover, Long> {
    List<RideStopover> findByRide_RideIdOrderByStopoverOrderAsc(Long rideId);
}


