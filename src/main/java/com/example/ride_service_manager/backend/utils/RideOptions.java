package com.example.ride_service_manager.backend.utils;

public class RideOptions {
    //Ride type (Standard or Premium) | Estimated price | Estimated travel time | Distance | Input field for the desired number of passengers
    String rideType;
    double estimatedPrice;
    int estimatedTravelTime;
    double distance;
    int numberOfPassengers;
    String rideMode; // instant ride OR scheduled ride

    String location, time;

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

    public String getRideMode() {
        return rideMode;
    }

    public void setRideMode(int rideMode) {
        if (rideMode == 1) {
            this.rideMode = "Instant";
        } else {
            this.rideMode = "Scheduled";
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
