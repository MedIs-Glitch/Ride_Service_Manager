package com.example.ride_service_manager;

import com.example.ride_service_manager.helpers.Statics;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterPassengerController {

    @FXML PasswordField password;
    @FXML PasswordField password2;
    @FXML
    TextField f1;
    @FXML
    TextField f2;
    @FXML
    TextField f3;
    @FXML
    TextField f4;

    @FXML
    ComboBox<String> wilayaCombo;

    @FXML
    public void initialize() {
        wilayaCombo.getItems().addAll(Statics.ALGERIA_WILAYAS);
    }


    @FXML
    protected void onRegisterClicked() {
        if(CheckFields()) {
            System.out.println("Register successful!");
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
        if (f1.getText().isEmpty()) {
            flag = false;
            f1.setStyle("-fx-border-color: red ;");
        }
        if (f2.getText().isEmpty()) {
            flag = false;
            f2.setStyle("-fx-border-color: red ;");
        }
        if (f3.getText().isEmpty()) {
            flag = false;
            f3.setStyle("-fx-border-color: red ;");
        }
        if (f4.getText().isEmpty()) {
            flag = false;
            f4.setStyle("-fx-border-color: red ;");
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
