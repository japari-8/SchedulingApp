module aparicio.schedulingapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens aparicio.schedulingapp to javafx.fxml;
    exports aparicio.schedulingapp;
}