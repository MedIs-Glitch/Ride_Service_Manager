package com.example.ride_service_manager.backend.utils;

public class RideHistory {

    //containing these:
    //passenger.getEmail() + "|" + driver.getFullName() + "|" + rideOptions.getRideType() + "|"
    //                + rideOptions.getRideMode() + "|" + passengerFeedback + "|"
    //                + rideOptions.getLocation() + "|"
    //                + time + "|"
    //                + "completed"
    //                +"\n";

    public String getEmail() {
        return email;
    }

    public String getRideType() {
        return rideType;
    }

    public void setRideType(String rideType) {
        this.rideType = rideType;
    }

    public String getRideMode() {
        return rideMode;
    }

    public void setRideMode(String rideMode) {
        this.rideMode = rideMode;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email, driverName, rideType, rideMode,
           feedback, location, time, status;

    public RideHistory(String email, String driverName, String rideType, String rideMode,
                       String feedback, String location, String time, String status) {
        this.email = email;
        this.driverName = driverName;
        this.rideType = rideType;
        this.rideMode = rideMode;
        this.feedback = feedback;
        this.location = location;
        this.time = time;
        this.status = status;

    }

    //constructor for these:
    // driver.getEmail() + "|" + passenger.getFullName() + "|" +
    //                rideOptions.getRideType() + "|" + rideOptions.getRideMode() + "|" +
    //                driverFeedback + "\n";
    public RideHistory(String email, String passengerName, String rideType, String rideMode,
                       String feedback) {
        this.email = email;
        this.driverName = passengerName;
        this.rideType = rideType;
        this.rideMode = rideMode;
        this.feedback = feedback;
    }

}
