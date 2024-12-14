package aparicio.controller;

import dao.CustomerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomer implements Initializable {

    public TextField firstNameAdd;
    public TextField lastNameAdd;
    public TextField addressAdd;
    public TextField postalCodeAdd;
    public TextField phoneNumAdd;
    public TextField customerId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        System.out.println("I am initialized");
    }

    public void onSaveAddCustomer(ActionEvent actionEvent) throws SQLException {

        String fullName = firstNameAdd.getText() + " " + lastNameAdd.getText();
        String address = addressAdd.getText();
        String postalCode = postalCodeAdd.getText();
        String phoneNum = phoneNumAdd.getText();



        //CustomerDAO.insertCust(fullName, address, postalCode, phoneNum, 4);


    }

    public void backToDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }

}