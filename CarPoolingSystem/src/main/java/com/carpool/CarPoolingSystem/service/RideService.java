package com.carpool.CarPoolingSystem.service;

import com.carpool.CarPoolingSystem.dto.RideResponse;
import com.carpool.CarPoolingSystem.model.Fares;
import com.carpool.CarPoolingSystem.model.Ride;
import com.carpool.CarPoolingSystem.model.User;
import com.carpool.CarPoolingSystem.repository.RideRepository;
import com.carpool.CarPoolingSystem.repository.UserRepository;
import com.carpool.CarPoolingSystem.model.RideStopover;
import com.carpool.CarPoolingSystem.repository.FareRepository;



import com.carpool.CarPoolingSystem.repository.RideStopoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private RideStopoverRepository stopoverRepository;
    @Autowired
    private FareRepository fareRepository;
    @Autowired
    private UserRepository userRepository;


    public void saveStopovers(long rideId, List<String> stopovers) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        stopovers.forEach(stopover -> {
            RideStopover newStopover = new RideStopover();
            newStopover.setRide(ride);
            newStopover.setStopoverLocation(stopover); // Use the correct method
            stopoverRepository.save(newStopover);
        });
    }


    public List<RideResponse> searchRides(String customerFrom, String customerTo, String fromDistrict, String toDistrict, LocalDate rideDate, int passengerCount){


        List<Ride> rides = rideRepository.searchRidesWithStopovers(fromDistrict.toLowerCase(), toDistrict.toLowerCase(), rideDate, passengerCount);

        return rides.stream().map(ride -> {
            RideResponse res = new RideResponse();
            res.setRideId(ride.getRideId());
            res.setFromLocation(ride.getFromLocation());
            res.setToLocation(ride.getToLocation());
            res.setRideDate(ride.getRideDate());
            res.setRideTime(ride.getRideTime());
            res.setSeatsAvailable(ride.getSeatsAvailable());
            res.setOwnerEmail(ride.getOwnerEmail());
          //  res.setBaseFare(ride.getBaseFare());
           // res.setDistanceInKm(ride.getDistanceInKm());
            Optional<User> userOptional = userRepository.findByEmail(ride.getOwnerEmail());
            userOptional.ifPresent(user -> res.setOwnerName(user.getName()));


            List<Fares> fares = fareRepository.findByRide_RideId(ride.getRideId());
            if (!fares.isEmpty()) {
                Fares fare = fares.get(0); // assuming 1 fare per ride
                res.setBaseFare(fare.getAdjustedFare());
                //res.setDistanceInKm(fare.getDistanceKm());
                double[] fareAndDist = calculateFareAndDistance(customerFrom, customerTo, ride);
                res.setCustomerFare(fareAndDist[0]);
                res.setCustomerDistance(fareAndDist[1]);
                res.setDisplayFrom(customerFrom);
                res.setDisplayTo(customerTo);

                // res.setDistanceInKm(calculateDistanceForCustomer(ride.getFromLocation(), ride.getToLocation(), ride));

            }
            // Adjust from/to if matched with stopovers
            List<RideStopover> stopovers = stopoverRepository.findByRide_RideIdOrderByStopoverOrderAsc(ride.getRideId());
            List<String> stopoverLocations = stopovers.stream()
                    .map(RideStopover::getStopoverLocation)
                    .collect(Collectors.toList());

            for (RideStopover stop : stopovers) {
                String stopLoc = stop.getStopoverLocation().toLowerCase();
                if (stopLoc.contains(fromDistrict.toLowerCase())) {
                    res.setFromLocation(stop.getStopoverLocation());
                }
                if (stopLoc.contains(toDistrict.toLowerCase())) {
                    res.setToLocation(stop.getStopoverLocation());
                }
            }

            return res;
        }).collect(Collectors.toList());
    }

    //IT IS ONLY FOR KIND AH PURPOSE FOR DISPLAYING KMS
    private double[] calculateFareAndDistance(String customerFrom, String customerTo, Ride ride) {
        List<String> path = new ArrayList<>();
        path.add(ride.getFromLocation());

        List<RideStopover> stopovers = stopoverRepository.findByRide_RideIdOrderByStopoverOrderAsc(ride.getRideId());
        for (RideStopover stop : stopovers) {
            path.add(stop.getStopoverLocation());
        }

        path.add(ride.getToLocation());

        int fromIndex = -1, toIndex = -1;
        for (int i = 0; i < path.size(); i++) {
            String loc = path.get(i).toLowerCase();
            if (loc.equals(customerFrom.toLowerCase())) fromIndex = i;
            if (loc.equals(customerTo.toLowerCase())) toIndex = i;
        }

        if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
            return new double[]{0.0, 0.0}; // invalid case
        }

        List<Fares> fares = fareRepository.findByRide_RideId(ride.getRideId());
        double totalFare = 0;
        double totalDistance = 0;

        for (int i = fromIndex; i < toIndex; i++) {
            String from = path.get(i);
            String to = path.get(i + 1);
            for (Fares fare : fares) {
                if (fare.getFromLocation().equalsIgnoreCase(from) && fare.getToLocation().equalsIgnoreCase(to)) {
                    totalFare += fare.getAdjustedFare();
                    totalDistance += fare.getDistanceKm();
                    break;
                }
            }
        }
        return new double[]{totalFare, totalDistance};
    }


}


