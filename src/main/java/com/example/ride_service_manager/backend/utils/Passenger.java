package com.example.ride_service_manager.backend.utils;

import java.io.Serializable;

public class Passenger implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String familyName;
    private String phoneNumber;
    private String email;
    private String password;
    private String wilaya;

    // No-arg constructor (useful for some serializers and frameworks)
    public Passenger() {
    }

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

    @Override
    public String toString() {
        return "Passenger{" +
                "firstName='" + firstName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", wilaya='" + wilaya + '\'' +
                '}';
    }
}
