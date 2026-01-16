module com.example.ride_service_manager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.rmi;


    opens com.example.ride_service_manager to javafx.fxml;
    exports com.example.ride_service_manager;

    // Export RMI interfaces so java.rmi can access them via reflection
    exports com.example.ride_service_manager.backend.rmi to java.rmi;
}