package com.carpool.CarPoolingSystem.controller;

import com.carpool.CarPoolingSystem.dto.StopoverRequest;
import com.carpool.CarPoolingSystem.model.Ride;
import com.carpool.CarPoolingSystem.model.RideStopover;
import com.carpool.CarPoolingSystem.repository.RideRepository;
import com.carpool.CarPoolingSystem.repository.RideStopoverRepository;
import com.carpool.CarPoolingSystem.repository.FareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ride")
@CrossOrigin(origins = "http://localhost:3000")
public class RideController {

    @Autowired
    private RideRepository rideRepo;

    @Autowired
    private RideStopoverRepository rideStopoverRepo;

    @Autowired
    private FareRepository rideFareRepo;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${google.maps.api.key}")
    private String googleApiKey;

    //1. Publish a ride with or without stopovers
    @PostMapping("/publish")
    public ResponseEntity<?> publishRideWithStopovers(@RequestBody StopoverRequest request) {
        try {
           // System.out.println("Received Publish Ride Request: " + request);

            Ride ride = new Ride();
            ride.setFromLocation(request.getFromLocation());
            ride.setToLocation(request.getToLocation());
            ride.setRideDate(request.getRideDate());
            ride.setRideTime(request.getRideTime());
            ride.setSeatsAvailable(request.getSeatsAvailable());
            ride.setOwnerEmail(request.getOwnerEmail());

            Ride savedRide = rideRepo.save(ride);
            System.out.println("Ride saved with ID: " + savedRide.getRideId());

            List<String> stops = request.getStopovers();
            if (stops != null && !stops.isEmpty()) {
                List<RideStopover> stopovers = new ArrayList<>();
                for (int i = 0; i < stops.size(); i++) {
                    RideStopover stop = new RideStopover();
                    stop.setRide(savedRide);
                    stop.setStopoverLocation(stops.get(i));
                    stop.setStopoverOrder(i + 1);
                    stopovers.add(stop);
                }
                rideStopoverRepo.saveAll(stopovers);
                System.out.println("Stopovers saved: " + stopovers.size());
            }

            return ResponseEntity.ok(savedRide.getRideId()); // Return rideId to frontend
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while publishing the ride");

        }
    }

    // 2. Add stopovers after ride is already created

    @PostMapping("/addStopovers")
    @Transactional
    public ResponseEntity<String> addStopovers(@RequestParam Long rideId, @RequestBody List<String> stopovers) {
        try {
            Optional<Ride> rideOptional = rideRepo.findById(rideId);
            if (!rideOptional.isPresent()) {
                return ResponseEntity.badRequest().body("Invalid rideId");
            }

            Ride ride = rideOptional.get();

            List<RideStopover> rideStopovers = new ArrayList<>();
            for (int i = 0; i < stopovers.size(); i++) {
                RideStopover stopover = new RideStopover();
                stopover.setRide(ride);
                stopover.setStopoverLocation(stopovers.get(i));
                stopover.setStopoverOrder(i + 1);
                rideStopovers.add(stopover);
            }

            rideStopoverRepo.saveAll(rideStopovers);

            return ResponseEntity.ok("Stopovers added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding stopovers: " + e.getMessage());
        }
    }
}
