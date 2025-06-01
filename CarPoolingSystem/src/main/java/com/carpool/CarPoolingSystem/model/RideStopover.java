package com.carpool.CarPoolingSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "ride_stopovers")
public class RideStopover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_id", nullable = false)
    private Ride ride;

    @Column(name = "stopover_location", nullable = false, length = 255)
    private String stopoverLocation;

    @Column(name = "stopover_order", nullable = false)
    private int stopoverOrder;


    public RideStopover() {
    }

    // Parameterized constructor
    public RideStopover(Ride ride, String stopoverLocation, int stopoverOrder) {
        this.ride = ride;
        this.stopoverLocation = stopoverLocation;
        this.stopoverOrder = stopoverOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public String getStopoverLocation() {
        return stopoverLocation;
    }

    public void setStopoverLocation(String stopoverLocation) {
        this.stopoverLocation = stopoverLocation;
    }

    public int getStopoverOrder() {
        return stopoverOrder;
    }

    public void setStopoverOrder(int stopoverOrder) {
        this.stopoverOrder = stopoverOrder;
    }
    @Override
    public String toString() {
        return "RideStopover{id=" + id + ", location=" + stopoverLocation + ", order=" + stopoverOrder + "}";
    }


}