package com.example.ride_service_manager.backend.server;

import com.example.ride_service_manager.backend.rmi.OptionServerRMI;
import com.example.ride_service_manager.backend.utils.RideOptions;

import java.io.*;
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
        // the options are: Ride type (Standard or Premium) | Estimated price | Estimated travel time | Distance | Input field for the desired number of passengers
        ArrayList<RideOptions> optionsList = new ArrayList<>();
        try {
            FileReader rideDataReader = new FileReader(DATA_FOLDER + RIDE_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(rideDataReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String rideType = parts[0].trim();
                    double estimatedPrice = Double.parseDouble(parts[1].trim());
                    int estimatedTravelTime = Integer.parseInt(parts[2].trim());
                    double distance = Double.parseDouble(parts[3].trim());
                    int maxPassengers = Integer.parseInt(parts[4].trim());

                    RideOptions option = new RideOptions(rideType, estimatedPrice, estimatedTravelTime, distance, maxPassengers);
                    optionsList.add(option);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.err.println("Error reading ride options data: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing ride options data: " + e.getMessage());
        }
        return optionsList;
    }
}
