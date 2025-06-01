package com.carpool.CarPoolingSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FareSegment {

    private String fromLocation;
    private String toLocation;
    private double baseFare;
    @JsonProperty("adjusted_fare")
    private double adjustedFare; // not adjusted_fare
    //private double adjustmentPercent; // optional but good for records
    public FareSegment() {}

    public FareSegment(String fromLocation, String toLocation, double baseFare, double adjustedFare) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.baseFare = baseFare;
       this.adjustedFare = adjustedFare;

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
