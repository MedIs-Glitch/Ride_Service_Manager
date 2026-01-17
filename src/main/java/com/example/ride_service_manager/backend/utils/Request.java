package com.example.ride_service_manager.backend.utils;

import java.io.Serializable;

public class Request implements Serializable {

    private static final long serialVersionUID = 1L;
    String passengerEmail;
    String driverEmail;
    String status; // ongoing, completed, cancelled

    RideHistory rideHistory;

    public Request(String passengerEmail, String driverEmail, String status) {
        this.passengerEmail = passengerEmail;
        this.driverEmail = driverEmail;
        this.status = status;
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

}
