module aparicio.schedulingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens aparicio.controller to javafx.fxml;
    exports aparicio.controller;
    opens aparicio.model to javafx.fxml;
    exports aparicio.model;
}