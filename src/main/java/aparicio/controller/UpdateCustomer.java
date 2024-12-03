package aparicio.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCustomer implements Initializable {

    public Label location;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");
    }

    public void onLogin(ActionEvent actionEvent) {
        System.out.println("I am clicked!");
    }
}