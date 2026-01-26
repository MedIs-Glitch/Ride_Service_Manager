package com.example.ride_service_manager;

import com.example.ride_service_manager.backend.utils.Driver;
import com.example.ride_service_manager.backend.utils.Request;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import java.util.concurrent.atomic.AtomicReference;

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
                Button acceptButton = new Button("Accept");
                acceptButton.setOnAction(e -> {
                    try {
                        Launcher.client.driverAcceptRequest(request.getDriverEmail(), request.getPassengerEmail());
                        // delete the request from the list
                        vbox.getChildren().remove(hbox);
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

                // make the select button
                Button refuseButton = new Button("Refuse");
                // change its style to red
                refuseButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                refuseButton.setOnAction(e -> {
                    try {
                        Launcher.client.driverRefuseRequest(request.getDriverEmail(), request.getPassengerEmail());
                        // delete the request from the list
                        vbox.getChildren().remove(hbox);
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
                hbox.getChildren().add(acceptButton);
                hbox.getChildren().add(refuseButton);
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

        AtomicReference<String> feedBackText = new AtomicReference<>("Neutral");

        // Button
        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(e -> {
            feedBackText.set(feedBack.getText());
            System.out.println("feedback entered: " + feedBackText);
            System.out.println("Request info: " + request.toString());
            try {
                Launcher.client.addCompletedRideToDriverHistory(driver, request, feedBackText.get());
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (NotBoundException ex) {
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

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
