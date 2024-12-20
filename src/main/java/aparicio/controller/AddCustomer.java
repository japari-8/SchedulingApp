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

//import static javafx.scene.control.skin.TableSkinUtils.getSelectionModel;

public class AddCustomer implements Initializable {

    public TextField firstNameAdd;
    public TextField lastNameAdd;
    public TextField addressAdd;
    public TextField postalCodeAdd;
    public TextField phoneNumAdd;
    public TextField customerId;
    public ComboBox<Country> countryCombo;
    public ComboBox<FirstLevelDivision> firstLevDivCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryCombo.setItems(CountryDAO.getAllCountries());

    }

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
            alert.setContentText(" Please select Country then select State/Province");
            alert.showAndWait();
        }
    }


    public void onSaveAddCustomer(ActionEvent actionEvent) throws SQLException, IOException {

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


    public void backToDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 750);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }


}