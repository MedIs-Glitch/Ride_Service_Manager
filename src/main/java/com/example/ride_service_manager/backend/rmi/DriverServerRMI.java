package com.example.ride_service_manager.backend.rmi;

import com.example.ride_service_manager.backend.utils.Driver;
import com.example.ride_service_manager.backend.utils.RideOptions;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface DriverServerRMI extends Remote {

    /**
     * Register a new Driver.
     * @return
     *      1. If registration is successful, return 1.
     *      2. If the email is already used, return -2.
     *      3. If the password and password confirmation do not match, return -1.
     *      4. If an unknown error occurs, return 0.
     * */
    public int registerDriver(String firstName, String familyName, String phoneNumber, String email,
                          String password, String passwordConfirmation, String wilaya,
                       String vehicleType, int estimatedArrivalTimeMinutes, String availability,
                       String picturePath) throws RemoteException;


    /**
     * Login for Drivers. Used to verify if the email exists and retrieve the password.
     * @param email The email of the Driver trying to log in.
     * @return password associated with the given email. If the email does not exist, return null.
     */
    String getDriverPassword(String email) throws RemoteException;

    /**
     * Retrieve Driver availability status by email.
     * @return The availability status of the Driver associated with the given email.
     * If the email does not exist, return null.
     * */
    String getDriverAvailability(String email) throws RemoteException;

    /**
     * Retrieve Driver details by email.
     * @return The Driver object associated with the given email.
     * If the email does not exist, return null.
     * */
    public Driver getDriverByEmail(String email) throws RemoteException;

    /**
     * Retrieve a list of Drivers matching the given RideOptions preferences.
     * @return A list of Drivers that match the specified RideOptions preferences.
     * */
    public ArrayList<Driver> getDriversWithPreference(RideOptions rideOptions) throws RemoteException;
}
