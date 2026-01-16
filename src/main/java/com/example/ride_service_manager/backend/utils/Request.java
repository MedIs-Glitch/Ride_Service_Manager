package com.example.ride_service_manager.backend.utils;

public class Request {
    String passengerEmail;
    String driverEmail;
    String status; // ongoing, completed, cancelled

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
