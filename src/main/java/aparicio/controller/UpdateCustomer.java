package aparicio.controller;

import aparicio.dao.CountryDAO;
import aparicio.dao.CustomerDAO;
import aparicio.dao.FirstLevDivDAO;
import aparicio.model.Customer;
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
import java.util.ResourceBundle;

/** This class lets a user update a customer.*/
public class UpdateCustomer implements Initializable {

    private static Customer selCustomer = null;
    public TextField firstNameUpdate;
    public TextField lastNameUpdate;
    public TextField addressUpdate;
    public TextField postalCodeUpdate;
    public TextField phoneNumUpdate;
    public TextField updateCustomerId;
    public ComboBox countryCombo2;
    public ComboBox firstLevDivCombo;

    /**This method receives a customer to update. */
    public static void passSelCustomer(Customer selCust) {
       selCustomer = selCust;
   }

    /**This method initializes the country and first level division combo boxes. This method also populate all the fields
     * with the customer data received.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String fullName = selCustomer.getName();
        String[] name = fullName.split(" ");
        firstNameUpdate.setText(name[0]);
        lastNameUpdate.setText(name[1]);
        addressUpdate.setText(selCustomer.getAddress());
        postalCodeUpdate.setText(selCustomer.getPostalCode());
        phoneNumUpdate.setText(selCustomer.getPostalCode());
        updateCustomerId.setText(Integer.toString(selCustomer.getCustomerId()));

        countryCombo2.setItems(CountryDAO.getAllCountries());

        String country = selCustomer.getCountry();
        int countryId = 0;

        if (country.equals("U.S")) {
            countryId = 1;
        }
        else if (country.equals("UK")) {
            countryId = 2;
        }
        else if (country.equals("Canada")){
            countryId = 3;
        }

        Country country1 = new Country(countryId, country);
        countryCombo2.setValue(country1);

        firstLevDivCombo.setValue(FirstLevDivDAO.getDivision(selCustomer.getDivisionId()));

    }

    /**This method changes the first level division combo box options based on the user's country selection. */
    public void displayFirstLevelDiv(MouseEvent actionEvent) {

        try {
            if (countryCombo2.getValue().toString().equals("U.S")) {
                firstLevDivCombo.setItems(FirstLevDivDAO.getUsFirstLevDiv());
            }
            else if (countryCombo2.getValue().toString().equals("UK")) {
                firstLevDivCombo.setItems(FirstLevDivDAO.getUkFirstLevDiv());
            }
            else if (countryCombo2.getValue().toString().equals("Canada")) {
                firstLevDivCombo.setItems(FirstLevDivDAO.getCanFirstLevDiv());
            }
        }
        catch(NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText(" Please select Country then select State/Province");
            alert.showAndWait();
        }

    }

    /**This method is called when the Save button is clicked. It saves the updated customer data and has validation checks for
     * empty fields.*/
    public void onSaveUpdateCustomer(ActionEvent actionEvent) throws IOException {

        try {
            String fullName = firstNameUpdate.getText() + " " + lastNameUpdate.getText();
            String address = addressUpdate.getText();
            String postalCode = postalCodeUpdate.getText();
            String phoneNum = phoneNumUpdate.getText();
            int divid = firstLevDivCombo.getSelectionModel().getSelectedItem().hashCode();
            int custid = Integer.parseInt(updateCustomerId.getText());

            CustomerDAO.updateCust(custid, fullName,address,postalCode, phoneNum, divid);

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
     * update customer request and redirects to the Dashboard screen.*/
    public void backToDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 750);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }


}