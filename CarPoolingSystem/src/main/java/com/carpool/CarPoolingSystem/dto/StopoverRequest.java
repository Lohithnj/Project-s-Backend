package com.carpool.CarPoolingSystem.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

public class StopoverRequest {
   private Long rideId;
    private String fromLocation;
    private String toLocation;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rideDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime rideTime;
    private int seatsAvailable;
    private String ownerEmail;
    private List<String> stopovers;

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public LocalDate getRideDate() {
        return rideDate;
    }

    public void setRideDate(LocalDate rideDate) {
        this.rideDate = rideDate;
    }

    public LocalTime getRideTime() {
        return rideTime;
    }

    public void setRideTime(LocalTime rideTime) {
        this.rideTime = rideTime;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public List<String> getStopovers() {
        return stopovers;
    }

    public void setStopovers(List<String> stopovers) {
        this.stopovers = stopovers;
    }
}


