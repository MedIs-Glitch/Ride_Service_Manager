package com.example.ride_service_manager.backend.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface DriverServerRMI extends Remote {
    public int registerDriver(String firstName, String familyName, String phoneNumber, String email,
                          String password, String passwordConfirmation, String wilaya,
                       String vehicleType, int estimatedArrivalTimeMinutes, String availability,
                       String picturePath) throws RemoteException;

    String getDriverPassword(String email) throws RemoteException;

    String getDriverAvailability(String email) throws RemoteException;
}
