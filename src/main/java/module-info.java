module com.example.ride_service_manager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.rmi;


    opens com.example.ride_service_manager to javafx.fxml;
    exports com.example.ride_service_manager;
    exports com.example.ride_service_manager.backend.rmi to java.rmi;
    exports com.example.ride_service_manager.backend.utils;
}