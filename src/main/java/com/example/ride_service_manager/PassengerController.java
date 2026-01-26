package com.example.ride_service_manager;

import com.example.ride_service_manager.backend.utils.Driver;
import com.example.ride_service_manager.backend.utils.Request;
import com.example.ride_service_manager.backend.utils.RideOptions;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.atomic.AtomicReference;

public class PassengerController {

    public static PassengerController instance;

    @FXML private Label status;
    @FXML private VBox contentArea;

    String statusText;

    @FXML protected void initialize(){
        try {
            statusText = Launcher.client.getLastRequestStatus(Launcher.client.passengerSession.getEmail());
            status.setText(statusText);
            System.out.println(status.getText());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        if(statusText.equals("accepted")){
            Platform.runLater(()->{
                showFeedBack();
            });

        }

        if(!statusText.equals("completed") && !statusText.equals("refused")){
            contentArea.setDisable(true);
        }
        else{
            contentArea.setDisable(false);
        }

        onPreferenceClicked();

        instance = this;
    }

    // 1. Initial Preference Button Click
    @FXML
    private void onPreferenceClicked() {
        try {
            contentArea.getChildren().setAll(SceneManager.getInstance().loadSubView("preference-view.fxml", contentArea));
        } catch (IOException e) {
            System.err.println("Unable to load preference view - OnPreferenceClicked() - PassengerController");
        }
    }

    @FXML
    protected void onAccountClicked(ActionEvent event) throws IOException {
        try {
            contentArea.getChildren().setAll(SceneManager.getInstance().loadSubView("passenger-account-view.fxml"));
        } catch (IOException e) {
            System.err.println("Unable to load Passenger Account view - OnPreferenceClicked() - PassengerController");
            e.printStackTrace();
        }
    }

    @FXML
    protected void onRideHistoryClicked(ActionEvent event) throws IOException {
        try {
            contentArea.getChildren().setAll(SceneManager.getInstance().loadSubView("ride-history-view.fxml"));
        } catch (IOException e) {
            System.err.println("Unable to load Passenger Account view - OnPreferenceClicked() - PassengerController");
            e.printStackTrace();
        }
    }



    // 3. Instant vs Scheduled (Server 3)
    private void showBookingOptions(boolean isInstant) {
        if (isInstant) {
            // Show TextField for Pickup Location
        } else {
            // Show DatePicker and TextField
        }
    }



    @FXML
    void onRefreshClicked(ActionEvent event) {
        initialize();
    }

    private void showFeedBack() {
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
            try {
                Launcher.client.addCompletedRideToPassengerHistory(Launcher.client.passengerSession, feedBackText.get());
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (NotBoundException ex) {
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            initialize();
            popupStage.close();
        });


        // Add nodes to layout
        root.getChildren().addAll(label, feedBack, submitBtn);

        // Scene and stage
        Scene scene = new Scene(root, 300, 150);
        popupStage.setScene(scene);
        popupStage.showAndWait(); // wait until user closes
    }
}
