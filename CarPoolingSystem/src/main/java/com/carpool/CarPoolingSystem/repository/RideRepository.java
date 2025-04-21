package com.carpool.CarPoolingSystem.repository;


import com.carpool.CarPoolingSystem.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Long> {
}
