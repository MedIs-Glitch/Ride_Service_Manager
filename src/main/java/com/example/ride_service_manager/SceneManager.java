package com.example.ride_service_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {
    public static void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource(fxmlFile)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        double stageCenterX = stage.getX() + stage.getWidth() / 2;
        double stageCenterY = stage.getY() + stage.getHeight() / 2;

        stage.setScene(new Scene(root));

        stage.setX(stageCenterX - stage.getWidth() / 2);
        stage.setY(stageCenterY - stage.getHeight() / 2);

        stage.show();
    }
}
