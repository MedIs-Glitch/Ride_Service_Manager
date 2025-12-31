package com.example.ride_service_manager;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class PreferenceController {

    @FXML private TextField desiredNumber;

    @FXML
    protected void onSelectClicked(ActionEvent event) {
        if(desiredNumber.getText().isEmpty()) {
            desiredNumber.setStyle("-fx-border-color: red ;");
        }
    }

    @FXML
    protected void onActionRestore(Event event) {
        Node source = (Node) event.getSource();
        source.setStyle("");
    }
}
