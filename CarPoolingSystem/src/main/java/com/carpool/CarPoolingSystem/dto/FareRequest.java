package com.carpool.CarPoolingSystem.dto;

import com.carpool.CarPoolingSystem.dto.FareSegment;

import java.util.List;

public class FareRequest {

    private String fromLocation;
    private String toLocation;
    private List<FareSegment> fareSegments;
    private double adjustmentPercent;
    private Long rideId;


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

    public List<FareSegment> getFareSegments() {
        return fareSegments ;
    }

    public void setFareSegments(List<FareSegment> fareSegments) {
        this.fareSegments=fareSegments;
    }

    public double getAdjustmentPercent() {
        return adjustmentPercent;
    }

    public void setAdjustmentPercent(double adjustmentPercent) {
        this.adjustmentPercent = adjustmentPercent;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

}
