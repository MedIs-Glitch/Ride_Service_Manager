package com.example.ride_service_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class PreferenceController {

    @FXML
    ComboBox<String> typeCombo;

    @FXML
    public void initialize() {
        typeCombo.getItems().addAll("Standard", "Premium");
    }

    @FXML
    public void onFindDriversClicked(ActionEvent actionEvent) {
        System.out.println("Driver found");
    }
}
