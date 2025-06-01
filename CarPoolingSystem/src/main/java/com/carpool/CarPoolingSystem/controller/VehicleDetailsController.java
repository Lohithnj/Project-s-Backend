package com.carpool.CarPoolingSystem.controller;

import com.carpool.CarPoolingSystem.model.VehicleDetails;
import com.carpool.CarPoolingSystem.service.VehicleDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleDetailsController {

    @Autowired
    private VehicleDetailsService vehicleDetailsService;

    @PostMapping("/save")
    public ResponseEntity<String> saveVehicleDetails(@RequestBody VehicleDetails vehicleDetails,
                                                     @RequestParam String email) {
        try {
            vehicleDetailsService.saveVehicleDetails(vehicleDetails, email);
            return ResponseEntity.ok("Vehicle details saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving vehicle details: " + e.getMessage());
        }
    }

    @GetMapping("/by-email")
    public ResponseEntity<List<VehicleDetails>> getVehicleDetails(@RequestParam String email) {
        return ResponseEntity.ok(vehicleDetailsService.getVehiclesByEmail(email));
    }
}
