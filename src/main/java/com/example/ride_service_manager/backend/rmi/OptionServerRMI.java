package com.example.ride_service_manager.backend.rmi;
import com.example.ride_service_manager.backend.utils.RideOptions;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface OptionServerRMI extends Remote {

    public ArrayList<RideOptions> getAvailableOptions() throws RemoteException;
}
