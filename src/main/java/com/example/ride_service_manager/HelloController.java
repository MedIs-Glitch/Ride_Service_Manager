package com.example.ride_service_manager;

import com.example.ride_service_manager.backend.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.rmi.NotBoundException;

public class HelloController {

    @FXML
    private TextField email;
    @FXML
    private PasswordField psw;

    @FXML
    public void initialize(){
        // Initialize Client
    }

    @FXML
    protected void onLoginClicked(ActionEvent event)  {
        try {
            int test = Launcher.client.login(email.getText(), psw.getText());
            if(test == 1) {
                System.out.println("Driver's Login successful!");
                System.out.println("Passenger's Login successful!");
                SceneManager.switchScene(event, "driver-dashboard-view.fxml");
            }
            else if(test == 2) {
                System.out.println("Passenger's Login successful!");
                SceneManager.switchScene(event, "passenger-dashboard-view.fxml");
            }
            else if(test == 0) System.out.println("Login failed! Incorrect email or password.");
        } catch (IOException e) {
            System.err.println("Unable to load preference view - OnLoginClicked() - HelloController");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
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
