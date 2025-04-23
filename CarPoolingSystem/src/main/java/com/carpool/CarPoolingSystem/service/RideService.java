package com.carpool.CarPoolingSystem.service;

import com.carpool.CarPoolingSystem.model.Ride;
import com.carpool.CarPoolingSystem.model.RideStopover;
import com.carpool.CarPoolingSystem.repository.RideRepository;
import com.carpool.CarPoolingSystem.repository.RideStopoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private RideStopoverRepository stopoverRepository;

    public void saveStopovers(long rideId, List<String> stopovers) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        stopovers.forEach(stopover -> {
            RideStopover newStopover = new RideStopover();
            newStopover.setRide(ride);
            newStopover.setStopoverLocation(stopover); // ✅ Use the correct method
            stopoverRepository.save(newStopover);
        });
    }

}


