package com.example.ride_service_manager.backend.utils;

import java.io.Serializable;

public class Request implements Serializable {

    private static final long serialVersionUID = 1L;
    String passengerEmail;
    String driverEmail;
    String status; // ongoing, completed, cancelled

    String rideType;
    String rideMode;
    String Location;
    String Time;


    public Request(String passengerEmail, String driverEmail, String status, String rideType, String rideMode, String Location, String Time) {
        this.passengerEmail = passengerEmail;
        this.driverEmail = driverEmail;
        this.status = status;
        this.rideType = rideType;
        this.rideMode = rideMode;
        this.Location = Location;
        this.Time = Time;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }
    public String getDriverEmail() {
        return driverEmail;
    }
    public String getStatus() {
        return status;
    }

    public String getRideType() {
        return rideType;
    }
    public String getRideMode() {
        return rideMode;
    }
    public String getLocation() {
        return Location;
    }
    public String getTime() {
        return Time;
    }


}
