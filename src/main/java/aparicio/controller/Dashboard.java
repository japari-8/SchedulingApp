package aparicio.controller;

import aparicio.dao.AppointmentDAO;
import aparicio.dao.CustomerDAO;
import aparicio.helper.JDBC;
import aparicio.model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import aparicio.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

import static aparicio.dao.AppointmentDAO.getAllAppointments;
import static aparicio.dao.CustomerDAO.getAllCustomerData;


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
    public ComboBox monthCombo;
    public RadioButton month;
    public Label messageLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerTableView.setItems(getAllCustomerData());
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

        ObservableList<Month> months = FXCollections.observableArrayList(Month.JANUARY, Month.FEBRUARY, Month.MARCH,
                Month.APRIL, Month.MAY, Month.JUNE, Month.JULY, Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER,
                Month.NOVEMBER, Month.DECEMBER);
        monthCombo.setItems(months);

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
        Scene scene = new Scene(root, 1000, 750);
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

    public void onDeleteAppnt(ActionEvent actionEvent) throws IOException {
        Appointment appnt = (Appointment) appntTableView.getSelectionModel().getSelectedItem();

        String appntIdAsSt = Integer.toString(appnt.getAppointmentId());
        String type = appnt.getType();

        if (appnt == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select an Appointment to delete.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This action will permanently delete the Appointment " +
                    "selected, do you want to continue?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                int appntId = appnt.getAppointmentId();
                AppointmentDAO.deleteAppointment(appntId);


                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error Dialog");
                alert2.setContentText("Appointment ID: " + appntIdAsSt + " of type " + type + " has been deleted.");
                alert2.showAndWait();
            }
        }

        appntTableView.setItems(getAllAppointments());
        messageLabel.setText("Message: Appointment ID: " + appntIdAsSt + " of type: " + type + " has been deleted.");
    }


    public void onAllAppntView(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 750);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public void onMonthAppntView(ActionEvent actionEvent) throws IOException {
        ObservableList<Appointment> listByMonth = FXCollections.observableArrayList();
        Month chMonth = (Month) monthCombo.getSelectionModel().getSelectedItem();

        if (chMonth == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a month from the drop down first.");
            alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 750);
            stage.setTitle("Dashboard");
            stage.setScene(scene);
            stage.show();
        }
        else {

            ObservableList<Appointment> allAppntsList = FXCollections.observableArrayList();
            allAppntsList = getAllAppointments();

            for (Appointment a : allAppntsList) {
                Month moFromApp = a.getStartDateTime().getMonth();

                //Month chMonth = chmonth.getValue();

                if (chMonth == moFromApp) {
                    listByMonth.add(a);
                }
            }
            appntTableView.setItems(listByMonth);
        }
    }


    public void onWeekAppntView(ActionEvent actionEvent) {
        Appointment appnt = (Appointment) appntTableView.getSelectionModel().getSelectedItem();
        DayOfWeek dof = appnt.getStartDateTime().getDayOfWeek();
        System.out.println(dof);
    }

    public void onExit(ActionEvent actionEvent) {

        JDBC.closeConnection();
        System.exit(0);
    }
}