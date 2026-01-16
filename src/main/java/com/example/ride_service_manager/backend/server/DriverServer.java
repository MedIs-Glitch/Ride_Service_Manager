package com.example.ride_service_manager.backend.server;

import com.example.ride_service_manager.backend.rmi.DriverServerRMI;
import com.example.ride_service_manager.backend.rmi.RideServerRMI;
import com.example.ride_service_manager.backend.utils.Driver;
import com.example.ride_service_manager.backend.utils.Passenger;
import com.example.ride_service_manager.backend.utils.RideHistory;
import com.example.ride_service_manager.backend.utils.RideOptions;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class DriverServer extends UnicastRemoteObject implements DriverServerRMI {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String DATA_FOLDER = "DriverServerData/";
    private static final String PICTURE_SUBFOLDER = "DriverServerData/Pictures/";
    private static final String DRIVER_DATA_FILE = "drivers.txt";
    private static final String DRIVER_RIDE_HISTORY = "driver_ride_history.txt";

    FileWriter driverDataWriter;

    protected DriverServer() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {
//            System.setProperty("java.rmi.server.hostname", "localhost");
            LocateRegistry.createRegistry(1096); //required port
            Naming.rebind("rmi://localhost:1096/DriverServer", new DriverServer());
            System.out.println("DriverServer ready");

        } catch (MalformedURLException | RemoteException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public int registerDriver(String firstName, String familyName, String phoneNumber, String email, String password, String passwordConfirmation, String wilaya, String vehicleType, int estimatedArrivalTimeMinutes, String availability, String picturePath) throws RemoteException {

        // Basic validation
        if (!password.equals(passwordConfirmation)) {
            System.err.println("Password and confirmation do not match for email: " + email);
            return -1; // Passwords do not match
        }

        FileReader driverDataReader;
        try {
            driverDataReader = new FileReader(DATA_FOLDER + DRIVER_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(driverDataReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 3 && parts[3].equals(email)) {
                    System.err.println("Email already registered in driver database: " + email);
                    bufferedReader.close();
                    return -2; // Email already registered
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            // File not found means no drivers registered yet, which is fine
        } catch (IOException e) {
            System.err.println("Error reading driver data: " + e.getMessage());
            return 0; // General error
        }

        // TODO: More validations can be added here (e.g., email format, password strength)
        //TODO: Problem if the email exists already in the passenger database

        try {
            RideServerRMI rideServer = (RideServerRMI) Naming.lookup("rmi://localhost:1098/RideServer");
            if (rideServer.getPassengerPassword(email) != null) {
                System.err.println("Email already registered in passenger database: " + email);
                return -3; // Email already registered as passenger
            }
        } catch (Exception e) {
            System.err.println("Error checking passenger database: " + e.getMessage());
            return 0; // General error
        }
        //--------------------------------------------------------------
        String driverRecord = firstName + "|" + familyName + "|" + phoneNumber + "|" + email + "|" + password + "|"
                + wilaya + "|" + vehicleType + "|" + estimatedArrivalTimeMinutes + "|" + availability
                + "|" + picturePath + "\n";
        try {
            File dataFolder = new File(DATA_FOLDER);
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            File pictureFolder = new File(PICTURE_SUBFOLDER);
            if (!pictureFolder.exists()) {
                pictureFolder.mkdirs();
            }
            driverDataWriter = new FileWriter(DATA_FOLDER + DRIVER_DATA_FILE, true);
            driverDataWriter.write(driverRecord);
            driverDataWriter.flush();
            driverDataWriter.close();
            System.out.println("Driver registered: " + email);
            return 1; // Success
        } catch (IOException e) {
            System.err.println("Error registering driver: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public String getDriverPassword(String email) throws RemoteException {
        FileReader driverDataReader;
        try {
            driverDataReader = new FileReader(DATA_FOLDER + DRIVER_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(driverDataReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 4 && parts[3].equals(email)) {
                    bufferedReader.close();
                    return parts[4]; // Return the password
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Driver data file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading driver data: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String getDriverAvailability(String email) throws RemoteException {
        FileReader driverDataReader;
        try {
            driverDataReader = new FileReader(DATA_FOLDER + DRIVER_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(driverDataReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 8 && parts[3].equals(email)) {
                    bufferedReader.close();
                    return parts[8]; // Return the availability
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Driver data file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading driver data: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Driver getDriverByEmail(String email) throws RemoteException {
        FileReader driverDataReader;
        try {
            driverDataReader = new FileReader(DATA_FOLDER + DRIVER_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(driverDataReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 3 && parts[3].equals(email)) {
                    Driver driver = new Driver(parts[0], parts[1], parts[2], parts[3], parts[4],
                            parts[5], parts[6], Integer.parseInt(parts[7]), parts[8], parts[9]);
                    bufferedReader.close();
                    return driver;
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Driver data file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading driver data: " + e.getMessage());
        }
        return null;

    }

    @Override
    public ArrayList<Driver> getDriversWithPreference(RideOptions rideOptions) throws RemoteException {
        ArrayList<Driver> matchingDrivers = new ArrayList<>();
        FileReader driverDataReader;
        try {
            driverDataReader = new FileReader(DATA_FOLDER + DRIVER_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(driverDataReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 8) {

                    // Example matching logic based on vehicle type and availability
//                    if (vehicleType.equalsIgnoreCase(rideOptions.getRideType()) &&
//                            availability.equalsIgnoreCase("Available")) {
                        Driver driver = new Driver(parts[0], parts[1], parts[2], parts[3], parts[4],
                                parts[5], parts[6], Integer.parseInt(parts[7]), parts[8], parts[9]);
                        matchingDrivers.add(driver);
//                    }
                }
            }
            bufferedReader.close();
            return matchingDrivers;
        } catch (FileNotFoundException e) {
            System.err.println("Driver data file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading driver data: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int addRideToDriverHistory(Driver driver, Passenger passenger, RideOptions rideOptions,
                                      String driverFeedback) throws RemoteException {
        String rideRecord = driver.getEmail() + "|" + passenger.getFullName() + "|" +
                rideOptions.getRideType() + "|" + rideOptions.getRideMode() + "|" +
                driverFeedback + "\n";
        try {
            FileWriter rideHistoryWriter = new FileWriter(DATA_FOLDER + DRIVER_RIDE_HISTORY, true);
            rideHistoryWriter.write(rideRecord);
            rideHistoryWriter.flush();
            rideHistoryWriter.close();
            System.out.println("Ride added to driver history: " + driver.getEmail());
            return 1; // Success
        } catch (IOException e) {
            System.err.println("Error adding ride to driver history: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public ArrayList<RideHistory> getRideHistory(String driverEmail) throws RemoteException {
        FileReader rideHistoryReader;
        try {
            rideHistoryReader = new FileReader(DATA_FOLDER + DRIVER_RIDE_HISTORY);
            BufferedReader bufferedReader = new BufferedReader(rideHistoryReader);
            String line;
            ArrayList<RideHistory> driverRideHistories = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 4 && parts[0].equals(driverEmail)) {
                    RideHistory rideHistoryEntry = new RideHistory(driverEmail,
                            parts[1], // driverName
                            parts[2],
                            parts[3], parts[4]);

                    driverRideHistories.add(rideHistoryEntry);
                }
            }
            bufferedReader.close();
            return driverRideHistories;
        } catch (FileNotFoundException e) {
            System.err.println("Driver ride history file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading driver ride history: " + e.getMessage());
        }
        return null;
    }
}
