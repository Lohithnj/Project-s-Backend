package com.carpool.CarPoolingSystem.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

public class RideResponse {

    private String ownerName;

    private Long rideId;
    private String fromLocation;
    private String toLocation;
    private LocalDate rideDate;
    private LocalTime rideTime;
    private int seatsAvailable;
    private String ownerEmail; // Optional
    private String profileIconUrl;     // Optional
     private Double baseFare;
    private Double distanceInKm;


    private double customerFare;
    private double customerDistance;
    private String displayFrom; // actual from to show
    private String displayTo;   // actual to to show

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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getProfileIconUrl() {
        return profileIconUrl;
    }

    public void setProfileIconUrl(String profileIconUrl) {
        this.profileIconUrl = profileIconUrl;
    }

    public Double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(Double baseFare) {
        this.baseFare = baseFare;
    }

    public Double getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(Double distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public double getCustomerDistance() {
        return customerDistance;
    }

    public double getCustomerFare() {
        return customerFare;
    }

    public void setCustomerFare(double customerFare) {
        this.customerFare = customerFare;
    }

    public void setCustomerDistance(double customerDistance) {
        this.customerDistance = customerDistance;
    }

    public String getDisplayFrom() {
        return displayFrom;
    }

    public String getDisplayTo() {
        return displayTo;
    }

    public void setDisplayFrom(String displayFrom) {
        this.displayFrom = displayFrom;
    }

    public void setDisplayTo(String displayTo) {
        this.displayTo = displayTo;
    }

    public String getOwnerName(String ownerName){
        return ownerName;
    }
    public void setOwnerName(){
        this.ownerName=ownerName;
    }

}
