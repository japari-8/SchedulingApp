module aparicio.schedulingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens aparicio.schedulingapp to javafx.fxml;
    exports aparicio.schedulingapp;
}