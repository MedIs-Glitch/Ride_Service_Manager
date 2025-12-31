package com.example.ride_service_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class PassengerController {

    @FXML private VBox contentArea;

    @FXML protected void initialize(){
        onPreferenceClicked();
    }

    // 1. Initial Preference Button Click
    @FXML
    private void onPreferenceClicked() {
        try {
            contentArea.getChildren().setAll(SceneManager.getInstance().loadSubView("preference-view.fxml"));
        } catch (IOException e) {
            System.err.println("Unable to load preference view - OnPreferenceClicked() - PassengerController");
        }
    }

    @FXML
    protected void onAccountClicked(ActionEvent event) throws IOException {
        try {
            contentArea.getChildren().setAll(SceneManager.getInstance().loadSubView("passenger-account-view.fxml"));
        } catch (IOException e) {
            System.err.println("Unable to load Passenger Account view - OnPreferenceClicked() - PassengerController");
            e.printStackTrace();
        }
    }

    // 3. Instant vs Scheduled (Server 3)
    private void showBookingOptions(boolean isInstant) {
        if (isInstant) {
            // Show TextField for Pickup Location
        } else {
            // Show DatePicker and TextField
        }
    }



    @FXML
    private void onSubmitFeedback(String type) {
        // type: "Positive", "Negative", "Neutral"
        System.out.println("Feedback submitted: " + type);
        // Show confirmation and return home
    }
}
