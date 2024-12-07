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
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 550);
        stage.setTitle("Add Customer Form");
        stage.setScene(scene);
        stage.show();
    }

    public void onUpdateCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/UpdateCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 550);
        stage.setTitle("Update Customer Form");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeleteCustomer(ActionEvent actionEvent) {
        System.out.println("Delete Customer button clicked");
    }

    public void onAddAppnt(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 700);
        stage.setTitle("Add Appointment Form");
        stage.setScene(scene);
        stage.show();
    }

    public void onUpdateAppnt(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/UpdateAppointment.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 700);
        stage.setTitle("Update Appointment Form");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeleteAppnt(ActionEvent actionEvent) {
        System.out.println("Delete Appnt button clicked");
    }
}