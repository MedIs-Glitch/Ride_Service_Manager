package com.example.ride_service_manager.backend.utils;

public class Passenger {
    String firstName;
    String familyName;
    String phoneNumber;
    String email;
    String password;
    String wilaya;

    public Passenger(String firstName, String familyName, String phoneNumber, String email, String password, String wilaya) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.wilaya = wilaya;
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

    public String getFullName() {
        return firstName + " " + familyName;
    }
}
