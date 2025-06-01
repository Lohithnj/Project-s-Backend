package com.carpool.CarPoolingSystem.controller;

import com.carpool.CarPoolingSystem.dto.StopoverRequest;
import com.carpool.CarPoolingSystem.dto.FareRequest;
import com.carpool.CarPoolingSystem.dto.FareSegment;
import com.carpool.CarPoolingSystem.model.Fares;
import com.carpool.CarPoolingSystem.model.Ride;
import com.carpool.CarPoolingSystem.repository.FareRepository;
import com.carpool.CarPoolingSystem.repository.RideRepository;
import com.carpool.CarPoolingSystem.service.FareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/api/fare")
@CrossOrigin(origins = "http://localhost:3000")
public class FareController {

    @Autowired
    private FareService fareService;
    @Autowired
    private FareRepository fareRepository;
    @Autowired
    private RideRepository rideRepository;

    //private FareRequest fareRequest;

    // Endpoint for fare calculation with adjustment (Base fare)
    @PostMapping("/calculateFare")
    public FareResponse calculateFare(@RequestBody FareRequest fareRequest) {
        // Get the base fare from the fare service
        double baseFare = fareService.calculateFare(fareRequest.getFromLocation(), fareRequest.getToLocation());

        // Adjust the fare based on the percentage adjustment
        double adjustedFare = fareService.adjustFare(baseFare);

        double distance = fareService.getDistanceFromGoogleMapsAPI(fareRequest.getFromLocation(),fareRequest.getToLocation());
       // Fares rideFare = new Fares(fareRequest.getFromLocation(), fareRequest.getToLocation(),distance, baseFare, adjustedFare); // 10.0 is mocked distance for example
        Fares rideFare = new Fares(fareRequest.getFromLocation(), fareRequest.getToLocation(),baseFare, adjustedFare); // 10.0 is mocked distance for example

        fareRepository.save(rideFare);

      //  System.out.println(adjustedFare);
        // Prepare response
        return new FareResponse(baseFare, adjustedFare);
    }

    // Endpoint for fare calculation with stopovers
    @PostMapping("/calculateFareWithStopovers")
    public List<FareSegment> calculateFareWithStopovers(@RequestBody StopoverRequest stopoverRequest) {
        // Calculate fare for the journey with stopovers
        return fareService.calculateFareWithStopovers(stopoverRequest.getFromLocation(), stopoverRequest.getToLocation(), stopoverRequest.getStopovers());
    }


@PostMapping("/saveSegmentFares")
public ResponseEntity<String> saveSegmentFares(@RequestBody FareRequest fareRequest) {

    try {
        Ride ride = rideRepository.findById(fareRequest.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        fareService.saveSegmentFares(fareRequest.getFareSegments(), ride);


        return ResponseEntity.status(HttpStatus.CREATED).body("Fare segments saved successfully");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error saving fare segments: " + e.getMessage());
    }
}




    class FareResponse {
        private double baseFare;
        private double adjustedFare;

        // Constructors
        public FareResponse() {
        }

        public FareResponse(double baseFare, double adjustedFare) {
            this.baseFare = baseFare;
            this.adjustedFare = adjustedFare;
        }

        // Getters and Setters
        public double getBaseFare() {
            return baseFare;
        }

        public void setBaseFare(double baseFare) {
            this.baseFare = baseFare;
        }

        public double getAdjustedFare() {
            return adjustedFare;
        }

        public void setAdjustedFare(double adjustedFare) {
            this.adjustedFare = adjustedFare;
        }
    }
}