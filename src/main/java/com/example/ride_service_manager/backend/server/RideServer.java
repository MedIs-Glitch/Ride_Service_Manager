package com.example.ride_service_manager.backend.server;

import com.example.ride_service_manager.backend.rmi.DriverServerRMI;
import com.example.ride_service_manager.backend.rmi.RideServerRMI;
import com.example.ride_service_manager.backend.utils.Driver;
import com.example.ride_service_manager.backend.utils.Passenger;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RideServer extends UnicastRemoteObject implements RideServerRMI {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String DATA_FOLDER = "RideServerData/";
    private static final String RIDE_DATA_FILE = "ride_history.txt";
    private static final String PASSENGER_DATA_FILE = "passengers.txt";

    FileWriter rideDataWriter;
    protected RideServer() throws RemoteException {
        super();
    }




    public static void main(String[] args) {
        try {

//            System.setProperty("java.rmi.server.hostname", "localhost");
            LocateRegistry.createRegistry(1098); //required port
            Naming.rebind("rmi://localhost:1098/RideServer", new RideServer());
            System.out.println("RideServer ready");

        } catch (MalformedURLException | RemoteException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public int registerPassenger(String firstName, String familyName, String phoneNumber, String email, String password, String passwordConfirmation, String wilaya) throws RemoteException {

        // Basic validation
        if (!password.equals(passwordConfirmation)) {
            System.err.println("Password and confirmation do not match for email: " + email);
            return -1; // Passwords do not match
        }
        FileReader passengerDataReader;
        try {
            passengerDataReader = new FileReader(DATA_FOLDER + PASSENGER_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(passengerDataReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 3 && parts[3].equals(email)) {
                    System.err.println("Email already registered in passenger database: " + email);
                    bufferedReader.close();
                    return -2; // Email already registered
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            // File might not exist yet, which is fine for the first registration
        }

        // TODO: More validations can be added here (e.g., email format, password strength)
        // TODO: Problem if the email exists already in the driver database

        //------------------------------------------
        String record = firstName + "|" + familyName + "|" + phoneNumber + "|" + email + "|" + password + "|" + wilaya + "\n";

        try {
            File dataFolder = new File(DATA_FOLDER);
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            rideDataWriter = new FileWriter(DATA_FOLDER + PASSENGER_DATA_FILE, true);
            rideDataWriter.write(record);
            rideDataWriter.flush();
            rideDataWriter.close();
            System.out.println("Passenger registered: " + email);

            return 1; // Indicate success
        } catch (Exception e) {
            System.err.println("Error registering passenger: " + e.getMessage());
        }

        return 0;
    }


    @Override
    public int login(String email, String password) throws RemoteException {
        FileReader passengerDataReader;
        try {
            passengerDataReader = new FileReader(DATA_FOLDER + PASSENGER_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(passengerDataReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 4 && parts[3].equals(email) && parts[4].equals(password)) {
                    System.out.println("Passenger logged in: " + email);
                    bufferedReader.close();
                    return 2; // Passenger login successful
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
        }

        // if the email is not for the passenger, we check for driver
        DriverServerRMI driverServer;
        try {
            driverServer = (DriverServerRMI) Naming.lookup("rmi://localhost:1098/DriverServer");
            String driverPassword = driverServer.getDriverPassword(email);
            if (driverPassword != null && driverPassword.equals(password)) {
                System.out.println("Driver logged in: " + email);
                return 1; // Driver login successful
            }
        } catch (Exception e) {
            System.err.println("Error during driver login: " + e.getMessage());
        }


        return 0;
    }

    @Override
    public Passenger getPassengerByEmail(String email) throws RemoteException {
        return null;
    }

    @Override
    public int determineRideMode(Driver driver) throws RemoteException {
        return 0;
    }
}
