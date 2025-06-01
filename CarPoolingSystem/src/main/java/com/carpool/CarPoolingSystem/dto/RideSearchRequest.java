package com.carpool.CarPoolingSystem.dto;

import java.time.LocalDate;

public class RideSearchRequest {
    private String customerFrom;
    private String customerTo;
    private String fromDistrict;
    private String toDistrict;
    private LocalDate rideDate;
    private int passengerCount;

    public String getFromDistrict() {
        return fromDistrict;
    }

    public String getToDistrict() {
        return toDistrict;
    }

    public LocalDate getRideDate() {
        return rideDate;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setFromDistrict(String fromDistrict) {
        this.fromDistrict = fromDistrict;
    }

    public void setToDistrict(String toDistrict) {
        this.toDistrict = toDistrict;
    }

    public void setRideDate(LocalDate rideDate) {
        this.rideDate = rideDate;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    public String getCustomerFrom() {
        return customerFrom;
    }

    public String getCustomerTo() {
        return customerTo;
    }

    public void setCustomerFrom(String customerFrom) {
        this.customerFrom = customerFrom;
    }
    public void setCustomerTo(String customerTo) {
        this.customerTo = customerTo;
    }
}

