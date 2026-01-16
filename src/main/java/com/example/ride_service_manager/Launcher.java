package com.example.ride_service_manager;

import com.example.ride_service_manager.backend.client.Client;
import javafx.application.Application;

import java.rmi.RemoteException;

public class Launcher {
    public static Client client;
    public static void main(String[] args) {
        try {
            client = new Client();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Application.launch(HelloApplication.class, args);
    }
}
