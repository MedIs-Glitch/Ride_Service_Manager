package com.example.ride_service_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class HelloController {

    @FXML
    protected void onLoginClicked(ActionEvent event)  {
        try {
            SceneManager.switchScene(event, "passenger-dashboard-view.fxml");
        } catch (IOException e) {
            System.err.println("Unable to load preference view - OnLoginClicked() - HelloController");
        }
    }

    @FXML
    protected void onRegisterPassengerClicked(ActionEvent event) {
        try {
            SceneManager.switchScene(event, "register-passenger-view.fxml");
        } catch (IOException e) {
            System.err.println("Unable to load preference view - OnPreferenceClicked() - PassengerController");
        }
    }

    @FXML
    protected void onRegisterDriverClicked(ActionEvent event) {
        try {
            SceneManager.switchScene(event, "register-driver-view.fxml");
        } catch (IOException e) {
            System.err.println("Unable to load preference view - OnPreferenceClicked() - PassengerController");
        }
    }

    @FXML
    protected void onForgotPSWClicked() {
        System.out.println("Forgot Password button clicked!");
    }

}
