package aparicio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomer implements Initializable {

    public Label location;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        System.out.println("I am initialized");
    }

    public void onSaveAddCustomer(ActionEvent actionEvent) {
    }

    public void onCancelAddCustomer(ActionEvent actionEvent) {

    }
}