package com.example.ride_service_manager.backend.client;

import com.example.ride_service_manager.Launcher;
import com.example.ride_service_manager.backend.rmi.DriverServerRMI;
import com.example.ride_service_manager.backend.rmi.OptionServerRMI;
import com.example.ride_service_manager.backend.rmi.RideServerRMI;
import com.example.ride_service_manager.backend.utils.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Client {
    private static RideServerRMI lookUpRideServer;
    private static DriverServerRMI lookUpDriverServer;
    private static OptionServerRMI lookUpOptionServer;
    public Passenger passengerSession;
    public Driver driverSession;
    public RideOptions rideOptions;
    public ArrayList<Driver> driversList;

    public Client() throws RemoteException {
        try {
            lookUpRideServer =(RideServerRMI) Naming.lookup("rmi://localhost:1098/RideServer");
            lookUpDriverServer =(DriverServerRMI) Naming.lookup("rmi://localhost:1096/DriverServer");
            lookUpOptionServer =(OptionServerRMI) Naming.lookup("rmi://localhost:1097/RideOptionServer");
        } catch (NotBoundException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public int registerPassenger(String firstName, String familyName, String phoneNumber, String email, String psw, String confPsw, String wilaya) throws MalformedURLException, NotBoundException, RemoteException {
        try{


            return lookUpRideServer.registerPassenger(firstName, familyName, phoneNumber, email, psw, confPsw, wilaya);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return -3; // Error
        }
    }

    public int registerDriver(String firstName, String familyName, String phoneNumber, String email, String psw, String confPsw, String wilaya, String vehicleType, int estimated, String availability, String picturePath) throws MalformedURLException, NotBoundException, RemoteException {
        try{

            return lookUpDriverServer.registerDriver(firstName, familyName, phoneNumber, email, psw, confPsw, wilaya, vehicleType, estimated, availability, picturePath);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return -3; // Error
        }
    }

    public int login(String email, String psw) throws MalformedURLException, NotBoundException, RemoteException {
        try{

            int test = lookUpRideServer.login(email, psw);
            if(test == 2){
                // Initialize passenger session
                passengerSession = lookUpRideServer.getPassengerByEmail(email);
                System.out.println(passengerSession.toString());
            }
            else if(test == 1){
                // Initialize driver session
                 driverSession = lookUpDriverServer.getDriverByEmail(email);
            }
            return test;
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return -3; // Error
        }
    }

    public ArrayList<RideOptions> getAvailableOptions() throws MalformedURLException, NotBoundException, RemoteException {
        try{
            return lookUpOptionServer.getAvailableOptions();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return null; // Error
        }
    }

    public ArrayList<Driver> getDriversWithPreference(RideOptions rideOptions) throws MalformedURLException, NotBoundException, RemoteException {
        try{
            System.out.println("Getting drivers with preference...");
            return lookUpDriverServer.getDriversWithPreference(rideOptions);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return null; // Error
        }
    }

    public int determineRideMode(Driver driver, RideOptions options) throws MalformedURLException, NotBoundException, RemoteException {
        try{
            int test = lookUpRideServer.determineRideMode(driver);
            options.setRideMode(test);
            System.out.println("Determined ride mode: " + test);
            return test;
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return 0;
        }
    }

    public ArrayList<Request> getRideRequestsForDriver(String email) throws MalformedURLException, NotBoundException, RemoteException {
        try{
            return null; //lookUpRideServer.getRideRequestsForDriver(email);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return null; // Error
        }
    }

    public ArrayList<RideHistory> getRideHistoryForPassenger(String email) throws MalformedURLException, NotBoundException, RemoteException {
        try{
            System.out.println("Getting ride history for passenger: " + email);
            return lookUpRideServer.getCompletedRidesOptionsHistoryForPassenger(email);
        }
        catch(RemoteException e){
            System.err.println(e.getMessage());
            System.out.println("NULL HH");
            return null; // Error
        }
    }

    public ArrayList<RideHistory> getRideHistoryForDriver(String email) throws MalformedURLException, NotBoundException, RemoteException {
        try{
            return lookUpDriverServer.getRideHistory(email);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return null; // Error
        }
    }

    public String getLastRequestStatus(String email) throws MalformedURLException, NotBoundException, RemoteException {
        try{
            System.out.println("Getting last request status for: " + email);
            return lookUpRideServer.getLastRequestStatus(email);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return null; // Error
        }
    }



}
