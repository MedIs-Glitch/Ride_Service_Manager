package com.example.ride_service_manager.backend.rmi;

import com.example.ride_service_manager.backend.utils.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RideServerRMI extends Remote {

    /**
     * Register a new Passenger.
     * @return
     *      1. If an unknown error occurs, return 0.
     *      2. If registration is successful, return 1.
     *      3. If the password and password confirmation do not match, return -1.
     *      4. If the email is already used, return -2.
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

    /**
     * Retrieve Passenger details by email.
     * @return The Passenger object associated with the given email.
     * */
    public Passenger getPassengerByEmail(String email) throws RemoteException;


    /**
     * Retrieve Passenger password by email.
     * @return password associated with the given email. If the email does not exist, return null.
     */
    String getPassengerPassword(String email) throws RemoteException;

    /**
     * Determine the ride mode (instant ride OR scheduled ride) according to Driver availability.
     * If the Driver is available then it is an instant ride.
     *         Else, it is a scheduled ride.
     * @return If the Driver is available, return 1 (instant ride).
     *        Else, return 2 (scheduled ride).
     * */
    public int determineRideMode(Driver driver) throws RemoteException;




    /**
     * Add an ongoing ride to the Passenger's ride history.
     * @return
     *      1. If the ride is successfully added to the history, return 1.
     *      2. If an error occurs while adding the ride, return 0.
     * */
    public int addOngoingRideToPassengerHistory(Driver driver, Passenger passenger, RideOptions rideOptions,
                                         String passengerFeedback) throws RemoteException;

    /**
     * Add a completed ride to the Passenger's ride history.
     * @return
     *      1. If the ride is successfully added to the history, return 1.
     *      2. If an error occurs while adding the ride, return 0.
     * */
    int addCompletedRideToPassengerHistory(Driver driver, Passenger passenger, RideOptions rideOptions,
                                           String passengerFeedback) throws RemoteException;

    public String getLastRequestStatus(String passengerEmail) throws RemoteException;

    public Request getAcceptedRequestForPassenger(String passengerEmail) throws RemoteException;

    public ArrayList<Request> getOngoingRequestsForDriver(String driverEmail) throws RemoteException;

    public int driveAcceptRequest(String driverEmail, String passengerEmail) throws RemoteException;

    public int passengerCancelRequest(String passengerEmail, String driverEmail) throws RemoteException;

    public int driverCancelRequest(String driverEmail, String passengerEmail) throws RemoteException;

    public ArrayList<RideHistory> getCompletedRidesOptionsHistoryForPassenger(String passengerEmail) throws RemoteException;

    int createRequest(String passengerEmail,String driverEmail, RideOptions rideOptions) throws RemoteException;

}
