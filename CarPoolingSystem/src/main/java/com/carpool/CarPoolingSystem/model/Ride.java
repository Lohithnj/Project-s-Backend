package com.carpool.CarPoolingSystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "ride")
@Data
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rideId;

    private String ownerEmail;
    private String fromLocation;
    private String toLocation;

    private LocalDate rideDate;
    private LocalTime rideTime;
    private int seatsAvailable;

    private Double baseFare;
    private Double distanceInKm;

//    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<RideStopover> stopovers;

    private java.sql.Timestamp createdAt = new java.sql.Timestamp(System.currentTimeMillis());
    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<RideStopover> stopovers = new java.util.ArrayList<>();

    public Ride() {
    }
    public Ride(String ownerEmail, String fromLocation, String toLocation,
                LocalDate rideDate, LocalTime rideTime, int seatsAvailable) {
        this.ownerEmail = ownerEmail;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.rideDate = rideDate;
        this.rideTime = rideTime;
        this.seatsAvailable = seatsAvailable;
    }
    public Long getRideId() {
        return rideId;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public LocalDate getRideDate() {
        return rideDate;
    }

    public LocalTime getRideTime() {
        return rideTime;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public void setRideDate(LocalDate rideDate) {
        this.rideDate = rideDate;
    }

    public void setRideTime(LocalTime rideTime) {
        this.rideTime = rideTime;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }


    //neww
    public Double getDistanceInKm() {
        return distanceInKm;
    }


    public Double getBaseFare() {
        return baseFare;
    }
}

