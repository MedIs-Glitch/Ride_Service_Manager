module com.example.ride_service_manager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;


    opens com.example.ride_service_manager to javafx.fxml;
    exports com.example.ride_service_manager;
}