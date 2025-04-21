package com.carpool.CarPoolingSystem.controller;


import com.carpool.CarPoolingSystem.model.Ride;
import com.carpool.CarPoolingSystem.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ride")
@CrossOrigin(origins = "http://localhost:3000")
public class RideController {

    @Autowired
    private RideRepository rideRepo;

    @PostMapping("/publish")
    public Ride publishRide(@RequestBody Ride ride) {
        return rideRepo.save(ride);
    }
}

