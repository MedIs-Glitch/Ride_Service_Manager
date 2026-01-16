package com.example.ride_service_manager.backend.utils;

public class RideOptions {
    //Ride type (Standard or Premium) | Estimated price | Estimated travel time | Distance | Input field for the desired number of passengers
    String rideType;
    double estimatedPrice;
    int estimatedTravelTime;
    double distance;
    int numberOfPassengers;

    public RideOptions(String rideType, double estimatedPrice, int estimatedTravelTime, double distance, int numberOfPassengers) {
        this.rideType = rideType;
        this.estimatedPrice = estimatedPrice;
        this.estimatedTravelTime = estimatedTravelTime;
        this.distance = distance;
        this.numberOfPassengers = numberOfPassengers;
    }

    public String getRideType() {
        return rideType;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public double getEstimatedPrice() {
        return estimatedPrice;
    }

    public int getEstimatedTravelTime() {
        return estimatedTravelTime;
    }

    public double getDistance() {
        return distance;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }
}
