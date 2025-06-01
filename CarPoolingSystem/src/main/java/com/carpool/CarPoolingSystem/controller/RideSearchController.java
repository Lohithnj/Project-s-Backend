package com.carpool.CarPoolingSystem.controller;

import com.carpool.CarPoolingSystem.dto.RideResponse;
import com.carpool.CarPoolingSystem.dto.RideSearchRequest;
import com.carpool.CarPoolingSystem.model.Ride;
import com.carpool.CarPoolingSystem.repository.RideRepository;
import com.carpool.CarPoolingSystem.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideSearchController {

    @Autowired
    private RideRepository rideRepository;
    @Autowired
 private RideService rideService;
@PostMapping("/search")
public ResponseEntity<List<RideResponse>> searchRides(@RequestBody RideSearchRequest request) {
    System.out.println("Received search request: " + request);
    List<RideResponse> results = rideService.searchRides(
            request.getCustomerFrom(),
            request.getCustomerTo(),
            request.getFromDistrict(),
            request.getToDistrict(),
            request.getRideDate(),
            request.getPassengerCount()
    );
    return ResponseEntity.ok(results);
}


}
