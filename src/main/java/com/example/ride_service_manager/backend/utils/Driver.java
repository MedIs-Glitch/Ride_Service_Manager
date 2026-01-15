package com.example.ride_service_manager.backend.utils;

public class Driver {
    String firstName;
    String familyName;
    String phoneNumber;
    String email;
    String password;
    String wilaya;
    String vehicleType;
    int estimatedArrivalTimeMinutes;
    String availability;
    String picturePath;

    public Driver(String firstName, String familyName, String phoneNumber, String email, String password,
                  String wilaya, String vehicleType, int estimatedArrivalTimeMinutes, String availability,
                  String picturePath) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.wilaya = wilaya;
        this.vehicleType = vehicleType;
        this.estimatedArrivalTimeMinutes = estimatedArrivalTimeMinutes;
        this.availability = availability;
        this.picturePath = picturePath;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getWilaya() {
        return wilaya;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public int getEstimatedArrivalTimeMinutes() {
        return estimatedArrivalTimeMinutes;
    }

    public String getAvailability() {
        return availability;
    }

    public String getPicturePath() {
        return picturePath;
    }
}
