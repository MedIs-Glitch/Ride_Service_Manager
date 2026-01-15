package com.example.ride_service_manager.backend.server;

import com.example.ride_service_manager.backend.rmi.DriverServerRMI;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DriverServer extends UnicastRemoteObject implements DriverServerRMI {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String DATA_FOLDER = "DriverServerData/";
    private static final String PICTURE_SUBFOLDER = "DriverServerData/Pictures/";
    private static final String DRIVER_DATA_FILE = "drivers.txt";

    FileWriter driverDataWriter;

    protected DriverServer() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {

//            System.setProperty("java.rmi.server.hostname", "localhost");
//            LocateRegistry.createRegistry(1098); //required port
            Naming.rebind("rmi://localhost/DriverServer", new DriverServer());
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
        return null;
    }

    @Override
    public String getDriverAvailability(String email) throws RemoteException {
        return null;
    }
}
