package aparicio.controller;

import aparicio.dao.CountryDAO;
import aparicio.dao.CustomerDAO;
import aparicio.dao.FirstLevDivDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import aparicio.model.Country;
import aparicio.model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** This class adds a customer to the database.*/
public class AddCustomer implements Initializable {

    public TextField firstNameAdd;
    public TextField lastNameAdd;
    public TextField addressAdd;
    public TextField postalCodeAdd;
    public TextField phoneNumAdd;
    public TextField customerId;
    public ComboBox<Country> countryCombo;
    public ComboBox<FirstLevelDivision> firstLevDivCombo;


    /**This method initializes the country combo box.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryCombo.setItems(CountryDAO.getAllCountries());

    }

    /**This method sets the first level division combo box. Displays only the divisions by selected country.*/
    public void displayFirstLevDiv(MouseEvent mouseEvent) {

        try {
            if (countryCombo.getSelectionModel().getSelectedItem().getCountryId() == 1) {
                firstLevDivCombo.setItems(FirstLevDivDAO.getUsFirstLevDiv());
            }
            else if (countryCombo.getSelectionModel().getSelectedItem().getCountryId() == 2){
                firstLevDivCombo.setItems(FirstLevDivDAO.getUkFirstLevDiv());
            }
            else if (countryCombo.getSelectionModel().getSelectedItem().getCountryId() == 3){
                firstLevDivCombo.setItems(FirstLevDivDAO.getCanFirstLevDiv());
            }
        }
        catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select Country then select State/Province");
            alert.showAndWait();
        }
    }

    /**This method is called when the Save button is clicked. It saves the customer data, has validation checks for
     * empty fields.*/
    public void onSaveAddCustomer(ActionEvent actionEvent) throws IOException {

        try {
            String fullName = firstNameAdd.getText() + " " + lastNameAdd.getText();
            String address = addressAdd.getText();
            String postalCode = postalCodeAdd.getText();
            String phoneNum = phoneNumAdd.getText();
            int divID = firstLevDivCombo.getSelectionModel().getSelectedItem().getDivisionId();

            CustomerDAO.addCust(fullName, address, postalCode, phoneNum, divID);

            Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 750);
            stage.setTitle("Dashboard");
            stage.setScene(scene);
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please make a selection in every field");
            alert.showAndWait();
        }
    }

    /**This method is called when the Cancel button is clicked. It cancels the
     * add customer request and redirects to the Dashboard screen.*/
    public void backToDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 750);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }


}