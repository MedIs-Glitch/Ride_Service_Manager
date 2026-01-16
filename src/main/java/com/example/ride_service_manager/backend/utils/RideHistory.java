package com.example.ride_service_manager.backend.utils;

public class RideHistory {

    //containing these:
    //passenger.getEmail() + "|" + driver.getFullName() + "|" + rideOptions.getRideType() + "|"
    //                + rideOptions.getRideMode() + "|" + passengerFeedback + "|"
    //                + rideOptions.getLocation() + "|"
    //                + time + "|"
    //                + "completed"
    //                +"\n";

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
