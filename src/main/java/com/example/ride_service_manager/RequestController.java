package com.example.ride_service_manager;

import com.example.ride_service_manager.backend.client.Client;
import com.example.ride_service_manager.backend.utils.Driver;
import com.example.ride_service_manager.backend.utils.Request;
import com.example.ride_service_manager.backend.utils.RideOptions;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class RequestController {

    @FXML private TextField desiredNumber;
    @FXML private VBox vbox;
    ArrayList<Request> requests = new ArrayList<>();


    @FXML void initialize() {
        try {
            requests = Launcher.client.getOngoingRequestsForDriver(Launcher.client.driverSession.getEmail());
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
        System.out.println(requests.size());
        for(Request request : requests) {
            if (vbox != null) {
                HBox hbox = new HBox();
                vbox.getChildren().add(hbox);
                Label label = new Label("Passenger: " + request.getPassengerEmail() + " Location: " + request.getLocation() + " Time: " + request.getTime() + " Mode:" + request.getRideMode());
                // Optional: style the label
                label.setStyle("-fx-padding: 5; -fx-font-size: 14;");
                hbox.getChildren().add(label);

                // make the select button
                Button button = new Button("Accept");
                button.setOnAction(e -> {
                    try {
                        Launcher.client.driverAcceptRequest(request.getDriverEmail(), request.getPassengerEmail());
                        // show feedback popup
                        showFeedBack(Launcher.client.driverSession, request);
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

    private void showFeedBack(Driver driver, Request request) {
        // Create new window (Stage)
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // blocks main window
        popupStage.setTitle("Enter Feedback");

        // Layout
        VBox root = new VBox(10); // spacing 10px
        root.setPadding(new Insets(15));

        // Input field
        Label label = new Label("Enter Feekback:");
        TextField feedBack = new TextField();

        // Button
        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(e -> {
            String feedBackText = feedBack.getText();
            System.out.println("feedback entered: " + feedBackText);
            try {
                System.out.println("Request info: " + request.toString());
                Launcher.client.addCompletedRideToDriverHistory(driver, request, feedBackText);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (NotBoundException ex) {
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            // TODO: do something with the location
            popupStage.close();
        });

        // Add nodes to layout
        root.getChildren().addAll(label, feedBack, submitBtn);

        // Scene and stage
        Scene scene = new Scene(root, 300, 150);
        popupStage.setScene(scene);
        popupStage.showAndWait(); // wait until user closes
    }
    private VBox contentArea; // <-- parent container

    public void setContentArea(VBox contentArea) {
        this.contentArea = contentArea;
    }
}
