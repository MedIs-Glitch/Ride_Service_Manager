package com.example.ride_service_manager;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class PassengerAccountController {

    @FXML
    private Circle pfp;

    @FXML
    private void initialize(){
        Image img = new Image(
                getClass().getResourceAsStream("/com/example/ride_service_manager/icons/pfp.png")
        );
        pfp.setFill(new ImagePattern(img));
    }

}
