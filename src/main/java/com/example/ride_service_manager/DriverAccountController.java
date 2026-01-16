package com.example.ride_service_manager;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class DriverAccountController {

    @FXML
    private Circle pfp;
    @FXML
    private Label nameLabel;
    @FXML
    private Label familyNameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label wilayaLabel;
    @FXML
    private Label type;
    @FXML
    private Label estimated;
    @FXML
    private Label availability;


    @FXML
    private void initialize(){
        Image img = new Image(
                getClass().getResourceAsStream("/com/example/ride_service_manager/icons/pfp.png")
        );
        pfp.setFill(new ImagePattern(img));

        // fill passenger's information
        nameLabel.setText(Launcher.client.driverSession.getFirstName());
        familyNameLabel.setText(Launcher.client.driverSession.getFamilyName());
        phoneLabel.setText(Launcher.client.driverSession.getPhoneNumber());
        emailLabel.setText(Launcher.client.driverSession.getEmail());
        wilayaLabel.setText(Launcher.client.driverSession.getWilaya());
        type.setText(Launcher.client.driverSession.getVehicleType());
        estimated.setText(String.valueOf(Launcher.client.driverSession.getEstimatedArrivalTimeMinutes()) + " min");
        availability.setText(Launcher.client.driverSession.getAvailability());

    }

}
