package com.carpool.CarPoolingSystem.controller;

import com.carpool.CarPoolingSystem.dto.StopoverRequest;
import com.carpool.CarPoolingSystem.model.Ride;
import com.carpool.CarPoolingSystem.model.RideStopover;
import com.carpool.CarPoolingSystem.repository.RideStopoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ride")
@CrossOrigin(origins = "http://localhost:3000")
public class RideStopoverController {

    @Autowired
    private RideStopoverRepository stopoverRepo;

    @PostMapping("/stopovers")
    public ResponseEntity<?> saveStopovers(@RequestBody StopoverRequest request) {
        try {
            for (String stop : request.getStopovers()) {
                if (stop != null && !stop.isBlank()) {
                    RideStopover s = new RideStopover();
                    Ride ride = new Ride();
                    ride.setRideId(request.getRideId());
                    s.setRide(ride);
                }
            }
            return ResponseEntity.ok("Stopovers saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to save stopovers.");
        }
    }
}
