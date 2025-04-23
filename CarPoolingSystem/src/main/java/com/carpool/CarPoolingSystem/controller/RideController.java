//package com.carpool.CarPoolingSystem.controller;
//
//import com.carpool.CarPoolingSystem.dto.StopoverRequest;
//import com.carpool.CarPoolingSystem.model.Ride;
//import com.carpool.CarPoolingSystem.model.RideStopover;
////import com.carpool.CarPoolingSystem.payload.request.StopoverRequest;
//import com.carpool.CarPoolingSystem.repository.RideRepository;
//import com.carpool.CarPoolingSystem.repository.RideStopoverRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/ride")
//@CrossOrigin(origins = "http://localhost:3000")
//public class RideController {
//
//    @Autowired
//    private RideRepository rideRepo;
//
//    @Autowired
//    private RideStopoverRepository rideStopoverRepo;
//
//    // ✅ For publishing ride without stopovers
//    @PostMapping("/publish")
//    public Ride publishRide(@RequestBody Ride ride) {
//        return rideRepo.save(ride);
//    }
//
//
//    // ✅ For publishing ride with stopovers
//    @PostMapping("/publishWithStops")
//    public ResponseEntity<?> publishRideWithStopovers(@RequestBody StopoverRequest request) {
//        Ride ride = new Ride();
//        ride.setFromLocation(request.getFromLocation());
//        ride.setToLocation(request.getToLocation());
//        ride.setRideDate(request.getRideDate());
//        ride.setRideTime(request.getRideTime());
//        ride.setSeatsAvailable(request.getSeatsAvailable());
//        ride.setOwnerEmail(request.getOwnerEmail());
//
//        Ride savedRide = rideRepo.save(ride);
//
//        List<RideStopover> stopovers = new ArrayList<>();
//        List<String> stops = request.getStopovers();
//        if (stops != null) {
//            for (int i = 0; i < stops.size(); i++) {
//                RideStopover stop = new RideStopover();
//                stop.setRide(savedRide);
//                stop.setStopoverLocation(stops.get(i));
//                stop.setStopoverOrder(i + 1);
//                stopovers.add(stop);
//            }
//            rideStopoverRepo.saveAll(stopovers);
//        }
//
//        return ResponseEntity.ok("Ride with stopovers published successfully!");
//    }
//}


package com.carpool.CarPoolingSystem.controller;

import com.carpool.CarPoolingSystem.dto.StopoverRequest;
import com.carpool.CarPoolingSystem.model.Ride;
import com.carpool.CarPoolingSystem.model.RideStopover;
import com.carpool.CarPoolingSystem.repository.RideRepository;
import com.carpool.CarPoolingSystem.repository.RideStopoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ride")
@CrossOrigin(origins = "http://localhost:3000")
public class RideController {

    @Autowired
    private RideRepository rideRepo;

    @Autowired
    private RideStopoverRepository rideStopoverRepo;

    // ✅ For publishing ride (with or without stopovers)
    @PostMapping("/publish")
    public ResponseEntity<?> publishRideWithStopovers(@RequestBody StopoverRequest request) {
        try {
            System.out.println("📥 Received Publish Ride Request: " + request);

            Ride ride = new Ride();
            ride.setFromLocation(request.getFromLocation());
            ride.setToLocation(request.getToLocation());
            ride.setRideDate(request.getRideDate());
            ride.setRideTime(request.getRideTime());
            ride.setSeatsAvailable(request.getSeatsAvailable());
            ride.setOwnerEmail(request.getOwnerEmail());

            Ride savedRide = rideRepo.save(ride);
            System.out.println("✅ Ride saved with ID: " + savedRide.getRideId());

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
                System.out.println("🛑 Stopovers saved: " + stopovers.size());
            }

            return ResponseEntity.ok("✅ Ride published successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Error occurred while publishing the ride");
        }
    }
}
