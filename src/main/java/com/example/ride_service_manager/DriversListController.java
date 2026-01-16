package com.example.ride_service_manager;

import com.example.ride_service_manager.backend.utils.Driver;
import com.example.ride_service_manager.backend.utils.RideOptions;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class DriversListController {

    @FXML private VBox vbox;


    @FXML void initialize() {
        addLabelToVBox();
    }

    @FXML
    protected void onActionRestore(Event event) {
        Node source = (Node) event.getSource();
        source.setStyle("");
    }

    public void addLabelToVBox() {
        ArrayList<Driver> driverList = Launcher.client.driversList;
        System.out.println(driverList.size());
        for(Driver driver : driverList) {
            if (vbox != null) {
                HBox hbox = new HBox();
                vbox.getChildren().add(hbox);
                Label label = new Label(driver.getFamilyName() + " - " + driver.getFirstName() + " - type: " + driver.getVehicleType() + " - avilability: " + driver.getAvailability());
                // Optional: style the label
                label.setStyle("-fx-padding: 5; -fx-font-size: 14;");
                hbox.getChildren().add(label);

                // make the select button
                Button button = new Button("select");
                button.setOnAction(e -> {
                    try {
                        int test = Launcher.client.determineRideMode(driver, Launcher.client.rideOptions);
                        if(test == 1) {
                            // pop up "enter location"
                            showLocationPopup();
                        }
                        else if(test == 2) {
                            // pop up "enter location & enter date and time"
                            showLocationAndTimePopup();
                        }

                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    } catch (NotBoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                hbox.getChildren().add(button);
            }
        }

    }

    private void showLocationPopup() {
        // Create new window (Stage)
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // blocks main window
        popupStage.setTitle("Enter Pickup Location");

        // Layout
        VBox root = new VBox(10); // spacing 10px
        root.setPadding(new Insets(15));

        // Input field
        Label label = new Label("Enter location:");
        TextField locationField = new TextField();

        // Button
        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(e -> {
            String location = locationField.getText();
            System.out.println("Location entered: " + location);
            // TODO: do something with the location
            popupStage.close();
        });

        // Add nodes to layout
        root.getChildren().addAll(label, locationField, submitBtn);

        // Scene and stage
        Scene scene = new Scene(root, 300, 150);
        popupStage.setScene(scene);
        popupStage.showAndWait(); // wait until user closes
    }

    private void showLocationAndTimePopup() {
        // Create new window (Stage)
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // blocks main window
        popupStage.setTitle("Enter Pickup Location & Time");

        // Layout
        VBox root = new VBox(10); // spacing 10px
        root.setPadding(new Insets(15));

        // Input fields
        Label locationLabel = new Label("Enter location:");
        TextField locationField = new TextField();

        Label timeLabel = new Label("Enter date & time:");
        TextField timeField = new TextField();

        // Button
        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(e -> {
            String location = locationField.getText();
            String time = timeField.getText();
            System.out.println("Location entered: " + location);
            System.out.println("Time entered: " + time);
            // TODO: do something with the location & time
            popupStage.close();
        });

        // Add nodes to layout
        root.getChildren().addAll(locationLabel, locationField, timeLabel, timeField, submitBtn);

        // Scene and stage
        Scene scene = new Scene(root, 300, 200);
        popupStage.setScene(scene);
        popupStage.showAndWait(); // wait until user closes
    }


}
