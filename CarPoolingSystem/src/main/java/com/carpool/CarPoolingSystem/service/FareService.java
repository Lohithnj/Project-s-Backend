package com.carpool.CarPoolingSystem.service;
import com.carpool.CarPoolingSystem.dto.FareSegment;
import com.carpool.CarPoolingSystem.model.Fares;
import com.carpool.CarPoolingSystem.model.Ride;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.carpool.CarPoolingSystem.repository.FareRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FareService {

    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;


    @Autowired
    private FareRepository fareRepository;

    private static final double BASE_FARE_PER_KM = 5.0;

    public double calculateFare(String fromLocation, String toLocation) {
        double distanceKm;
        distanceKm = getDistanceFromGoogleMapsAPI(fromLocation, toLocation);
        double baseFare;
        baseFare = distanceKm * BASE_FARE_PER_KM;
        return baseFare;
    }

    public double adjustFare(double baseFare) {
        double adjustmentPercent = 30.0; // Default 30%
        return baseFare + (baseFare * adjustmentPercent / 100);
    }

    public List<FareSegment> calculateFareWithStopovers(String fromLocation, String toLocation, List<String> stopovers) {
        List<FareSegment> fareSegments = new ArrayList<>();

        if (!stopovers.isEmpty()) {
            // First segment
            double base1 = calculateFare(fromLocation, stopovers.get(0));
            double adjusted1 = adjustFare(base1);
            fareSegments.add(new FareSegment(fromLocation, stopovers.get(0), base1, adjusted1));

            // Between stopovers
            for (int i = 0; i < stopovers.size() - 1; i++) {
                double base = calculateFare(stopovers.get(i), stopovers.get(i + 1));
                double adjusted = adjustFare(base);
                fareSegments.add(new FareSegment(stopovers.get(i), stopovers.get(i + 1), base, adjusted));
            }

            // Last segment
            double baseLast = calculateFare(stopovers.get(stopovers.size() - 1), toLocation);
            double adjustedLast = adjustFare(baseLast);
            fareSegments.add(new FareSegment(stopovers.get(stopovers.size() - 1), toLocation, baseLast, adjustedLast));
        }

        return fareSegments;
    }


    public double getDistanceFromGoogleMapsAPI(String fromLocation, String toLocation) {
        String url = String.format("https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&key=%s",
                fromLocation, toLocation, googleMapsApiKey);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        // Use Jackson to parse the JSON response
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response);

            if (jsonResponse.get("status").asText().equals("OK")) {
                JsonNode element = jsonResponse.get("rows").get(0).get("elements").get(0);
                if (element.get("status").asText().equals("OK")) {
                    // Distance in meters
                    int distanceInMeters = element.get("distance").get("value").asInt();
                    double distanceInKm = distanceInMeters / 1000.0; // Convert meters to kilometers
                    return distanceInKm;
                } else {
                    throw new RuntimeException("Error fetching distance data: " + element.get("status").asText());
                }
            } else {
                throw new RuntimeException("Error fetching data from Google Maps API: " + jsonResponse.get("status").asText());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing Google Maps API response", e);
        }
    }

    @Autowired
    private ModelMapper modelMapper;


public void saveSegmentFares(List<FareSegment> segmentFares, Ride ride) {
//    segmentFares.forEach(segment ->
//            System.out.println("From: " + segment.getFromLocation() +
//                    ", To: " + segment.getToLocation() +
//                    ", BaseFare: " + segment.getBaseFare() +
//                    ", AdjustedFare: " + segment.getAdjustedFare()));

    List<Fares> faresList = segmentFares.stream()
            .map(segment -> {
                Fares fare = new Fares();
                fare.setRide(ride);
                fare.setFromLocation(segment.getFromLocation());
                fare.setToLocation(segment.getToLocation());
                fare.setBaseFare(segment.getBaseFare());
                //fare.setAdjustedFare(segment.getAdjustedFare());
                fare.setAdjustedFare(adjustFare(segment.getBaseFare()));


                // Optional: Calculate and set distance if required by DB
                double distance = calculateDistance(segment.getFromLocation(), segment.getToLocation());
                fare.setDistanceKm(distance);
                System.out.println("Saving segment: from " + segment.getFromLocation() +
                        " to " + segment.getToLocation() +
                        " | Base Fare: " + segment.getBaseFare() +
                        " | Adjusted Fare: " + segment.getAdjustedFare());

                return fare;
            })
            .collect(Collectors.toList());

    fareRepository.saveAll(faresList);
}
    private double calculateDistance(String from, String to) {
        return getDistanceFromGoogleMapsAPI(from, to); // re-use existing method
    }
}
