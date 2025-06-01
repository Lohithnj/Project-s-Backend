package com.carpool.CarPoolingSystem.util;
public class FareUtil {

    private static final double FARE_PER_KM = 5.0;

    public static double calculateBaseFare(double distanceKm) {
        return distanceKm * FARE_PER_KM;
    }

    public static double applyAdjustment(double baseFare, double percentageChange) {
        return baseFare + (baseFare * percentageChange / 100);
    }
}

