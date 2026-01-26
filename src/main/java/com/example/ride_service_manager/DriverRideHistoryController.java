package com.example.ride_service_manager;

import com.example.ride_service_manager.backend.utils.RideHistory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class DriverRideHistoryController {

    ArrayList<RideHistory> rideHistories = new ArrayList<>();

    @FXML private VBox vbox;


    @FXML
    void initialize() {
        // Example of adding labels dynamically to the VBox
        try {
            System.out.println("emaill:" + Launcher.client.driverSession.getEmail());
            rideHistories = Launcher.client.getRideHistoryForDriver(Launcher.client.driverSession.getEmail());
            System.out.println("ride history size:" + rideHistories.size());
            fillRideHistory();
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void addLabelToVBox(String text) {
        if (vbox != null) {
            Label label = new Label(text);
            // Optional: style the label
            label.setStyle("-fx-padding: 5; -fx-font-size: 14;");
            vbox.getChildren().add(label);
        }
    }

    void fillRideHistory() {
        for (RideHistory rideHistory : rideHistories) {
            // String email, driverName, rideType, rideMode,
            //           feedback, location, time, status;
            String rideInfo =
                    " | Passenger: " + rideHistory.getDriverName() +
                    " | Type: " + rideHistory.getRideType() +
                    " | Mode: " + rideHistory.getRideMode() +
                    " | Feedback: " + rideHistory.getFeedback() +
                    " | Time: " + rideHistory.getTime();
            addLabelToVBox(rideInfo);
        }
    }
}
