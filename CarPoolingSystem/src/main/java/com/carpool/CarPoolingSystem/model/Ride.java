package com.carpool.CarPoolingSystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
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

    private java.sql.Timestamp createdAt = new java.sql.Timestamp(System.currentTimeMillis());
}

