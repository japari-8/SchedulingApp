package aparicio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class UpdateAppointment implements Initializable {



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LocalTime startTimeStart = LocalTime.of(8,0);
        LocalTime startTimeEnd = LocalTime.of(21,0);

        while (startTimeStart.isBefore(startTimeEnd.plusSeconds(1))) {
            startTimeCombo.getItems().add(startTimeStart);
            startTimeStart = startTimeStart.plusHours(1);
        }

    }

    
    public void onSaveUpdateAppnt(ActionEvent actionEvent) {

    }

    public void backToDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 750);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}