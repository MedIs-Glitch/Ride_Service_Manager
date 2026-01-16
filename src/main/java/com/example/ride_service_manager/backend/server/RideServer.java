package com.example.ride_service_manager.backend.server;

import com.example.ride_service_manager.backend.rmi.DriverServerRMI;
import com.example.ride_service_manager.backend.rmi.RideServerRMI;
import com.example.ride_service_manager.backend.utils.*;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RideServer extends UnicastRemoteObject implements RideServerRMI {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String DATA_FOLDER = "RideServerData/";
    private static final String RIDE_DATA_FILE = "ride_history.txt";
    private static final String PASSENGER_DATA_FILE = "passengers.txt";
    private static final String REQUEST_DATA_FILE = "requests.txt";


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

        try {
            DriverServerRMI driverServer;
            driverServer = (DriverServerRMI) Naming.lookup("rmi://localhost:1096/DriverServer");
            String driverPassword = driverServer.getDriverPassword(email);
            if (driverPassword != null) {
                System.err.println("Email already registered in driver database: " + email);
                return -2; // Email already registered
            }
        } catch (Exception e) {
            // It's fine if we can't find the driver server or the email doesn't exist there
        }

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

        return 0; // General error
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
        FileReader passengerDataReader;
        try {
            passengerDataReader = new FileReader(DATA_FOLDER + PASSENGER_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(passengerDataReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 5 && parts[3].equals(email)) {
                    Passenger passenger = new Passenger(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                    bufferedReader.close();
                    return passenger; // Return the passenger object
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.err.println("Error retrieving passenger data: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String getPassengerPassword(String email) throws RemoteException {
        FileReader passengerDataReader;
        try {
            passengerDataReader = new FileReader(DATA_FOLDER + PASSENGER_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(passengerDataReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 4 && parts[3].equals(email)) {
                    bufferedReader.close();
                    return parts[4]; // Return the password
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.err.println("Error retrieving passenger password: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int determineRideMode(Driver driver) throws RemoteException {
        if (driver == null) {
            System.err.println("Driver object is null");
            return 0; // Error
        }
        if (driver.getAvailability().equals("Available")) {
            System.out.println("Driver is available: " + driver.getEmail());
            return 1; // Driver is available
        } else {
            System.out.println("Driver is not available: " + driver.getEmail());
            return 2; // Driver is not available
        }
    }

    @Override
    public int addCompletedRideToPassengerHistory(Driver driver, Passenger passenger, RideOptions rideOptions,
                                                  String passengerFeedback) {
        if (driver == null || passenger == null || rideOptions == null) {
            System.err.println("One or more parameters are null");
            return 0; // Error
        }
        String time = (rideOptions.getTime() != null) ? rideOptions.getTime() : "N/A";
        String record = passenger.getEmail() + "|" + driver.getFullName() + "|" + rideOptions.getRideType() + "|"
                + rideOptions.getRideMode() + "|" + passengerFeedback + "|"
                + rideOptions.getLocation() + "|"
                + time + "|"
                + "completed"
                +"\n";

        try{
            File dataFolder = new File(DATA_FOLDER);
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            rideDataWriter = new FileWriter(DATA_FOLDER + RIDE_DATA_FILE, true);
            rideDataWriter.write(record);
            rideDataWriter.flush();
            rideDataWriter.close();
            System.out.println("Ride added to passenger history: " + passenger.getEmail());

            return 1; // Indicate success
        } catch (Exception e) {
            System.err.println("Error adding ride to passenger history: " + e.getMessage());
        }

        return 0;
    }

    @Override
    public String getLastRequestStatus(String passengerEmail) throws RemoteException {
        try {
            FileReader requestDataReader = new FileReader(DATA_FOLDER + REQUEST_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(requestDataReader);
            String line;
            String lastStatus = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals(passengerEmail)) {
                    lastStatus = parts[2]; // Update lastStatus with the latest found status
                }
            }
            bufferedReader.close();
            return lastStatus; // Return the last found status
        } catch (Exception e) {
            System.err.println("Error retrieving last request status: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Request getOngoingRequestForPassenger(String passengerEmail) throws RemoteException {
        try {
            FileReader requestDataReader = new FileReader(DATA_FOLDER + REQUEST_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(requestDataReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals(passengerEmail) && parts[2].equals("ongoing")) {
                    Request request = new Request(parts[0], parts[1], parts[2]);
                    bufferedReader.close();
                    return request; // Return the ongoing request object
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.err.println("Error retrieving ongoing request: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Request getOngoingRequestForDriver(String driverEmail) throws RemoteException {
        try {
            FileReader requestDataReader = new FileReader(DATA_FOLDER + REQUEST_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(requestDataReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[1].equals(driverEmail) && parts[2].equals("ongoing")) {
                    Request request = new Request(parts[0], parts[1], parts[2]);
                    bufferedReader.close();
                    return request; // Return the ongoing request object
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.err.println("Error retrieving ongoing request: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int driveAcceptRequest(String driverEmail, String passengerEmail) throws RemoteException {
        // overwrite the request status to "accepted" in the requests.txt file, if found, don't remove the content of the file
        try {
            FileReader requestDataReader = new FileReader(DATA_FOLDER + REQUEST_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(requestDataReader);
            StringBuilder fileContent = new StringBuilder();
            String line;
            boolean requestFound = false;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals(passengerEmail) && parts[1].equals(driverEmail) && parts[2].equals("ongoing")) {
                    fileContent.append(parts[0]).append("|").append(parts[1]).append("|accepted").append("\n");
                    requestFound = true;
                } else {
                    fileContent.append(line).append("\n");
                }
            }
            bufferedReader.close();

            if (requestFound) {
                FileWriter requestDataWriter = new FileWriter(DATA_FOLDER + REQUEST_DATA_FILE);
                requestDataWriter.write(fileContent.toString());
                requestDataWriter.flush();
                requestDataWriter.close();
                System.out.println("Request accepted by driver: " + driverEmail + " for passenger: " + passengerEmail);
                return 1; // Indicate success
            } else {
                System.err.println("No matching ongoing request found for driver: " + driverEmail + " and passenger: " + passengerEmail);
            }
        } catch (Exception e) {
            System.err.println("Error accepting request: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int passengerCancelRequest(String passengerEmail, String driverEmail) throws RemoteException {
        try {
            FileReader requestDataReader = new FileReader(DATA_FOLDER + REQUEST_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(requestDataReader);
            StringBuilder fileContent = new StringBuilder();
            String line;
            boolean requestFound = false;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals(passengerEmail) && parts[1].equals(driverEmail) && parts[2].equals("ongoing")) {
                    fileContent.append(parts[0]).append("|").append(parts[1]).append("|cancelled").append("\n");
                    requestFound = true;
                } else {
                    fileContent.append(line).append("\n");
                }
            }
            bufferedReader.close();

            if (requestFound) {
                FileWriter requestDataWriter = new FileWriter(DATA_FOLDER + REQUEST_DATA_FILE);
                requestDataWriter.write(fileContent.toString());
                requestDataWriter.flush();
                requestDataWriter.close();
                System.out.println("Request cancelled by passenger: " + passengerEmail + " for driver: " + driverEmail);
                return 1; // Indicate success
            } else {
                System.err.println("No matching ongoing request found for passenger: " + passengerEmail + " and driver: " + driverEmail);
            }
        } catch (Exception e) {
            System.err.println("Error cancelling request: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int driverCancelRequest(String driverEmail, String passengerEmail) throws RemoteException {
        try {
            FileReader requestDataReader = new FileReader(DATA_FOLDER + REQUEST_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(requestDataReader);
            StringBuilder fileContent = new StringBuilder();
            String line;
            boolean requestFound = false;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals(passengerEmail) && parts[1].equals(driverEmail) && parts[2].equals("ongoing")) {
                    fileContent.append(parts[0]).append("|").append(parts[1]).append("|cancelled").append("\n");
                    requestFound = true;
                } else {
                    fileContent.append(line).append("\n");
                }
            }
            bufferedReader.close();

            if (requestFound) {
                FileWriter requestDataWriter = new FileWriter(DATA_FOLDER + REQUEST_DATA_FILE);
                requestDataWriter.write(fileContent.toString());
                requestDataWriter.flush();
                requestDataWriter.close();
                System.out.println("Request cancelled by driver: " + driverEmail + " for passenger: " + passengerEmail);
                return 1; // Indicate success
            } else {
                System.err.println("No matching ongoing request found for driver: " + driverEmail + " and passenger: " + passengerEmail);
            }
        } catch (Exception e) {
            System.err.println("Error cancelling request: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public ArrayList<RideHistory> getCompletedRidesOptionsHistoryForPassenger(String passengerEmail) throws RemoteException {
        try {
            FileReader rideDataReader = new FileReader(DATA_FOLDER + RIDE_DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(rideDataReader);
            String line;
            ArrayList<RideHistory> rideHistories = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals(passengerEmail) && parts[7].equals("completed")) {
                    RideHistory rideHistory = new RideHistory(
                            parts[0], // passengerEmail
                            parts[1], // driverName
                            parts[2], // rideType
                            parts[3], // rideMode
                            parts[4], // passengerFeedback
                            parts[5], // location
                            parts[6],  // time
                            parts[7]  // status
                    );
                    rideHistories.add(rideHistory);
                }
            }
            bufferedReader.close();
            return rideHistories; // Return the list of completed rides
        } catch (Exception e) {
            System.err.println("Error retrieving completed rides history: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int addOngoingRideToPassengerHistory(Driver driver, Passenger passenger, RideOptions rideOptions,
                                                  String passengerFeedback) throws RemoteException {
        if (driver == null || passenger == null || rideOptions == null) {
            System.err.println("One or more parameters are null");
            return 0; // Error
        }
        String time = (rideOptions.getTime() != null) ? rideOptions.getTime() : "N/A";
        String record = passenger.getEmail() + "|" + driver.getFullName() + "|" + rideOptions.getRideType() + "|"
                + rideOptions.getRideMode() + "|" + passengerFeedback + "|"
                + rideOptions.getLocation() + "|"
                + time + "|"
                + "ongoing"
                +"\n";

        try{
            File dataFolder = new File(DATA_FOLDER);
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            rideDataWriter = new FileWriter(DATA_FOLDER + RIDE_DATA_FILE, true);
            rideDataWriter.write(record);
            rideDataWriter.flush();
            rideDataWriter.close();
            System.out.println("Ongoing ride added to passenger history: " + passenger.getEmail());

            return 1; // Indicate success
        } catch (Exception e) {
            System.err.println("Error adding ongoing ride to passenger history: " + e.getMessage());
        }

        return 0;
    }






}
