package com.example.ride_service_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class PassengerController {

    @FXML private VBox contentArea;
    @FXML private Label welcomeLabel;

    // 1. Initial Preference Button Click
    @FXML
    private void onPreferenceClicked() throws IOException {
        // Here you would call Server 1
        loadSubView("preference-view.fxml");
    }

    // 3. Instant vs Scheduled (Server 3)
    private void showBookingOptions(boolean isInstant) {
        if (isInstant) {
            // Show TextField for Pickup Location
        } else {
            // Show DatePicker and TextField
        }
    }

    // Utility to swap the center content
    private void loadSubView(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        contentArea.getChildren().setAll((Node) loader.load());
    }

    @FXML
    private void onSubmitFeedback(String type) {
        // type: "Positive", "Negative", "Neutral"
        System.out.println("Feedback submitted: " + type);
        // Show confirmation and return home
    }
}
