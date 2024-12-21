package aparicio.controller;

import aparicio.dao.CustomerDAO;
import aparicio.helper.JDBC;
import aparicio.model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import aparicio.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static aparicio.dao.AppointmentDAO.getAllAppointments;


public class Dashboard implements Initializable {

    public TableView customerTableView;
    public TableColumn customerIdCol;
    public TableColumn fullNameCol;
    public TableColumn addressCol;
    public TableColumn postalCodeCol;
    public TableColumn phoneNumCol;
    public TableColumn divisionIdCol;
    public TableColumn countryCol;
    
    public TableView appntTableView;
    public TableColumn AppntCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn typeCol;
    public TableColumn startDateTimeCol;
    public TableColumn endDateTimeCol;
    public TableColumn customerIdCol2;
    public TableColumn userIdCol;
    public TableColumn contactIdCol;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerTableView.setItems(CustomerDAO.getAllCustomerData());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));

        appntTableView.setItems(getAllAppointments());
        AppntCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdCol2.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));

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

        Customer custToUpdate = (Customer) customerTableView.getSelectionModel().getSelectedItem();

        if (custToUpdate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a Customer to update.");
            alert.showAndWait();
        }
        else {
            UpdateCustomer.passSelCustomer(custToUpdate);

            Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/UpdateCustomer.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 700, 550);
            stage.setTitle("Update Customer Form");
            stage.setScene(scene);
            stage.show();
        }

    }


    public void onDeleteCustomer(ActionEvent actionEvent) throws IOException {
       Customer cus = (Customer) customerTableView.getSelectionModel().getSelectedItem();

        if (cus == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a Customer to delete.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This action will permanently delete customer from database, do you want to continue?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                int cusID = cus.getCustomerId();
                CustomerDAO.deleteCustomerData(cusID);
            }
        }

        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }



    public void onAddAppnt(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 700);
        stage.setTitle("Add Appointment Form");
        stage.setScene(scene);
        stage.show();
    }

    public void onUpdateAppnt(ActionEvent actionEvent) throws IOException {

        Appointment appntToUpdate = (Appointment) appntTableView.getSelectionModel().getSelectedItem();


        if (appntToUpdate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select an Appointment to update.");
            alert.showAndWait();
        }
        else {
            UpdateAppointment.passSelAppnt(appntToUpdate);

            Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/UpdateAppointment.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 700);
            stage.setTitle("Update Appointment Form");
            stage.setScene(scene);
            stage.show();
        }


    }

    public void onDeleteAppnt(ActionEvent actionEvent) {
        System.out.println("Delete Appnt button clicked");
    }

    public void onAllAppntView(ActionEvent actionEvent) {
    }

    public void onMonthAppntView(ActionEvent actionEvent) {
    }

    public void onWeekAppntView(ActionEvent actionEvent) {
    }

    public void onExit(ActionEvent actionEvent) {

        JDBC.closeConnection();
        System.exit(0);
    }
}