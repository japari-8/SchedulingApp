module aparicio.schedulingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens aparicio.controller to javafx.fxml;
    exports aparicio.controller;
}