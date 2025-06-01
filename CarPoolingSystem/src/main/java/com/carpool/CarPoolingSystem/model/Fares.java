package com.carpool.CarPoolingSystem.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ride_fares")
public class Fares {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    @Column(nullable = false)
    private String fromLocation;

    @Column(nullable = false)
    private String toLocation;

    @Column(nullable = false)
    private double distanceKm;

    @Column(nullable = false)
    private double baseFare;

    @Column(name = "adjusted_fare")
    private double adjustedFare;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


    public Fares() {
    }


    public Fares(String fromLocation, String toLocation,
                 double baseFare, double adjustedFare) {
       // this.ride = ride;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
      //  this.distanceKm = distanceKm;
        this.baseFare = baseFare;
        this.adjustedFare = adjustedFare;
    }
//public Fares(String fromLocation, String toLocation,
//             double distanceKm, double baseFare, double adjustedFare) {
//    this.fromLocation = fromLocation;
//    this.toLocation = toLocation;
//    this.distanceKm = distanceKm;
//    this.baseFare = baseFare;
//    this.adjustedFare = adjustedFare;
// }

    public Long getId() {
        return id;
    }

    public Ride getRide() {
        return ride;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public double getAdjustedFare() {
        return adjustedFare;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    public void setAdjustedFare(double adjustedFare) {
        this.adjustedFare = adjustedFare;
    }

    // no setter for createdAt as it should be immutable after creation
}
