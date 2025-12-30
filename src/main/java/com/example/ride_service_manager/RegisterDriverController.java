package com.example.ride_service_manager;

import com.example.ride_service_manager.helpers.Statics;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

public class RegisterDriverController {

    @FXML PasswordField password;
    @FXML PasswordField password2;
    @FXML
    TextField firstName;
    @FXML
    TextField familyName;
    @FXML
    TextField email;
    @FXML
    TextField estimatedTime;
    @FXML
    TextField phoneNumber;

    @FXML
    ComboBox<String> wilayaCombo;
    @FXML
    ComboBox<String> vehicleTypeCombo;

    @FXML
    ImageView profileImageView;

    @FXML
    RadioButton yesRadioBtn, noRadioBtn;

    @FXML
    public void initialize() {
        wilayaCombo.getItems().addAll(Statics.ALGERIA_WILAYAS);
        vehicleTypeCombo.getItems().addAll(Statics.VEHICLE_TYPES);
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
        if (estimatedTime.getText().isEmpty()) {
            flag = false;
            estimatedTime.setStyle("-fx-border-color: red ;");
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
        if(vehicleTypeCombo.getValue() == null) {
            flag = false;
            vehicleTypeCombo.setStyle("-fx-border-color: red ;");
        }

        return flag;
    }

    @FXML
    protected void onActionRestore(Event event) {
        Node source = (Node) event.getSource();
        source.setStyle("");
    }

    @FXML
    protected void onSelectImageClicked() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Profile Picture");

        // 1. Add filters so the user only sees image files
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        // 2. Get the file from the dialog
        // We pass the window of the button so the dialog is "modal" (stays on top)
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            // 3. Convert the File to a JavaFX Image
            // 'true' at the end preserves the aspect ratio so the image isn't stretched
            Image image = new Image(selectedFile.toURI().toString(), 80, 80, true, true);

            // 4. Set the image into the ImageView
            profileImageView.setImage(image);
        }
    }
}

