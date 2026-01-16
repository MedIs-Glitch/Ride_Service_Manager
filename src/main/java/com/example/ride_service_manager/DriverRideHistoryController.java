package com.example.ride_service_manager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DriverRideHistoryController {

    @FXML private VBox vbox;

    @FXML
    void initialize() {
        // Example of adding labels dynamically to the VBox
        addLabelToVBox("Driver: Ahmed | Type: Premium | Mode: Instant | Feedback: Positive");
        addLabelToVBox("Driver: Ahmed | Type: Premium | Mode: Instant | Feedback: Positive");
        addLabelToVBox("Driver: Ahmed | Type: Premium | Mode: Instant | Feedback: Positive");
    }

    public void addLabelToVBox(String text) {
        if (vbox != null) {
            Label label = new Label(text);
            // Optional: style the label
            label.setStyle("-fx-padding: 5; -fx-font-size: 14;");
            vbox.getChildren().add(label);
        }
    }
}
