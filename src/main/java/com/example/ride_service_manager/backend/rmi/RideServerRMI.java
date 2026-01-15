package com.example.ride_service_manager.backend.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RideServerRMI extends Remote {

    /**
     * Register a new Passenger.
     * @return
     *      1. If registration is successful, return 1.
     *      2. If the email is already used, return 0.
     *      3. If the password and password confirmation do not match, return -1.
     * */
    public int registerPassenger(String firstName, String familyName, String phoneNumber, String email,
                          String password, String passwordConfirmation, String wilaya) throws RemoteException;

    /**
     * Login for both Drivers and Passengers.
     * @param email The email of the user trying to log in.
     * @param password The password provided for login.
     *
     * @return
     *     1. If the email belongs to a Driver and password matches, return 1.
     *     2. If the email belongs to a Passenger and password matches, return 2.
     *     3. If the email does not exist or password does not match, return 0.
     * */
    public int login(String email, String password) throws RemoteException;
}
