package com.example.ride_service_manager;

import com.example.ride_service_manager.backend.client.Client;
import com.example.ride_service_manager.backend.utils.Driver;
import com.example.ride_service_manager.backend.utils.RideOptions;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class RequestController {

    @FXML private TextField desiredNumber;
    @FXML private VBox vbox;
    ArrayList<RideOptions> rideOptions = new ArrayList<>();


    @FXML void initialize() {
        try {
            rideOptions = Launcher.client.getAvailableOptions();
            addLabelToVBox();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onActionRestore(Event event) {
        Node source = (Node) event.getSource();
        source.setStyle("");
    }

    public void addLabelToVBox() {
        System.out.println(rideOptions.size());
        for(RideOptions option : rideOptions) {
            if (vbox != null) {
                HBox hbox = new HBox();
                vbox.getChildren().add(hbox);
                Label label = new Label(option.getRideType() + " - " + option.getDistance() + " - Price: " + option.getEstimatedPrice() + " - Estimated Time: " + option.getEstimatedTravelTime() + " mins");
                // Optional: style the label
                label.setStyle("-fx-padding: 5; -fx-font-size: 14;");
                hbox.getChildren().add(label);

                // make the select button
                Button button = new Button("select");
                button.setOnAction(e -> {
                    option.setNumberOfPassengers(Integer.parseInt(desiredNumber.getText()));
                    try {
                        Launcher.client.rideOptions = option;
                        ArrayList<Driver> driversList = Launcher.client.getDriversWithPreference(option);
                        System.out.println(driversList.size() + " drivers found with the selected preference.");

                        contentArea.getChildren().setAll(
                                SceneManager.getInstance()
                                        .loadSubView("drivers-list-view.fxml", contentArea)
                        );
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (NotBoundException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                hbox.getChildren().add(button);
            }
        }

    }

    private VBox contentArea; // <-- parent container

    public void setContentArea(VBox contentArea) {
        this.contentArea = contentArea;
    }
}
