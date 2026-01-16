package com.example.ride_service_manager;

import com.example.ride_service_manager.backend.client.Client;
import com.example.ride_service_manager.helpers.Statics;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RegisterPassengerController {

    @FXML PasswordField password;
    @FXML PasswordField password2;
    @FXML
    TextField firstName;
    @FXML
    TextField familyName;
    @FXML
    TextField email;
    @FXML
    TextField phoneNumber;

    @FXML
    ComboBox<String> wilayaCombo;

    @FXML
    public void initialize()  {
        wilayaCombo.getItems().addAll(Statics.ALGERIA_WILAYAS);

    }


    @FXML
    protected void onRegisterClicked() throws MalformedURLException, NotBoundException, RemoteException {
        if(CheckFields()) {
            int test = Launcher.client.registerPassenger(firstName.getText(), familyName.getText(), phoneNumber.getText(), email.getText(),password.getText(), password2.getText(),wilayaCombo.getValue());
            if(test == 1) System.out.println("Register successful!");
            else if(test == 0) System.out.println("Register already exists!");
            else if(test == -2) System.out.println("Register failed!, test = -2");
        }
        else {
            System.out.println("Register failed!");
        }
    }

    @FXML
    protected void onLoginClicked(ActionEvent event) {
        try{
            SceneManager.switchScene(event, "hello-view.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean CheckPasswords() {
        boolean flag = true;

        String psw = password.getText();
        String psw2 = password2.getText();
        if (!psw.equals(psw2)) {
            flag = false;

            // change border color to red
            password.setStyle("-fx-border-color: red ;");
            password2.setStyle("-fx-border-color: red ;");
        }

        return flag;
    }

    private boolean CheckFields() {
        boolean flag = true;

        // check if fields are empty
        if (firstName.getText().isEmpty()) {
            flag = false;
            firstName.setStyle("-fx-border-color: red ;");
        }
        if (familyName.getText().isEmpty()) {
            flag = false;
            familyName.setStyle("-fx-border-color: red ;");
        }
        if (email.getText().isEmpty()) {
            flag = false;
            email.setStyle("-fx-border-color: red ;");
        }
        if (phoneNumber.getText().isEmpty()) {
            flag = false;
            phoneNumber.setStyle("-fx-border-color: red ;");
        }
        if(password.getText().isEmpty()){
            flag = false;
            password.setStyle("-fx-border-color: red ;");
        }
        if(password2.getText().isEmpty()){
            flag = false;
            password2.setStyle("-fx-border-color: red ;");
        }
        flag = CheckPasswords();

        if(wilayaCombo.getValue() == null) {
            flag = false;
            wilayaCombo.setStyle("-fx-border-color: red ;");
        }

        return flag;
    }

    @FXML
    protected void onActionRestore(Event event) {
        Node source = (Node) event.getSource();
        source.setStyle("");
    }
}
