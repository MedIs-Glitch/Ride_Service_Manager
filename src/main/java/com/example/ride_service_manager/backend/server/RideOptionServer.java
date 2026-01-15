package com.example.ride_service_manager.backend.server;

import com.example.ride_service_manager.backend.rmi.OptionServerRMI;
import com.example.ride_service_manager.backend.utils.RideOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serial;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RideOptionServer extends UnicastRemoteObject implements OptionServerRMI {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String DATA_FOLDER = "RideOptionServerData/";
    private static final String RIDE_DATA_FILE = "ride_options.txt";

    public RideOptionServer() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {

//            System.setProperty("java.rmi.server.hostname", "localhost");
//            LocateRegistry.createRegistry(1098); //required port
            Naming.rebind("rmi://localhost/RideOptionServer", new RideOptionServer());
            System.out.println("RideOptionServer ready");

        } catch (MalformedURLException | RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<RideOptions> getAvailableOptions() throws RemoteException {
        return null;
    }
}
