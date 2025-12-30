package com.example.ride_service_manager;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;

import java.io.IOException;

public class HelloController {

    @FXML
    protected void onLoginClicked() {
        System.out.println("Login button clicked!");
    }

    @FXML
    protected void onRegisterPassengerClicked(ActionEvent event) {
        try {
            SceneManager.switchScene(event, "register-passenger-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onRegisterDriverClicked(ActionEvent event) {
        try {
            SceneManager.switchScene(event, "register-driver-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onForgotPSWClicked() {
        System.out.println("Forgot Password button clicked!");
    }

}
