package com.example.ride_service_manager.backend.client;

import com.example.ride_service_manager.backend.rmi.DriverServerRMI;
import com.example.ride_service_manager.backend.rmi.OptionServerRMI;
import com.example.ride_service_manager.backend.rmi.RideServerRMI;
import com.example.ride_service_manager.backend.utils.Driver;
import com.example.ride_service_manager.backend.utils.Passenger;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    private static RideServerRMI lookUpRideServer;
    private static DriverServerRMI lookUpDriverServer;
    private static OptionServerRMI lookUpOptionServer;
    public Passenger passengerSession;
    public Driver driverSession;

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

    public int login(String email, String psw) throws MalformedURLException, NotBoundException, RemoteException {
        try{

            int test = lookUpRideServer.login(email, psw);
            if(test == 2){
                // Initialize passenger session
                passengerSession = lookUpRideServer.getPassengerByEmail(email);
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
}
