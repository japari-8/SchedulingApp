package aparicio.controller;

import aparicio.dao.AppointmentDAO;
import aparicio.dao.CustomerDAO;
import aparicio.helper.JDBC;
import aparicio.model.Appointment;
import aparicio.model.User;
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
import static java.time.YearMonth.now;

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
    public static User logedInUser;
    private static boolean firstTime = true;

    public static void passLogedUser(User userLogedIn) {
        logedInUser = userLogedIn;
    }

    private void setAppointmentAlert() {
        if (!firstTime) {
            return;
        }
        firstTime = false;

        ObservableList<Appointment> apptsByUserList = FXCollections.observableArrayList();
        apptsByUserList = AppointmentDAO.getAppntByUserId(logedInUser.getUserId());

        LocalDateTime current = LocalDateTime.now();
        LocalDateTime in15Min = current.plusMinutes(15);

        for (Appointment b : apptsByUserList) {
            LocalDateTime appntLdt = b.getStartDateTime();

            if ( (appntLdt.isAfter(current) && appntLdt.isBefore(in15Min)) || (appntLdt.isEqual(in15Min)) ){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("You have an upcoming Appointment. Id: " + b.getAppointmentId() + "Date: "
                        + b.getStartDateTime().toLocalDate() + "Time: " + b.getStartDateTime().toLocalTime());
                alert.showAndWait();
            }
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention Dialog");
        alert.setContentText("You have No upcoming Appointment.");
        alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setAppointmentAlert();

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
        ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
        allAppts = getAllAppointments();
        ObservableList<Appointment> aptsForNext30Days = FXCollections.observableArrayList();

        LocalDateTime currentLdt = LocalDateTime.now();
        LocalDateTime ldtIn30Days = currentLdt.plusDays(30);

        for (Appointment a : allAppts) {
            LocalDateTime listLdt = a.getStartDateTime();

            if ( (listLdt.isAfter(currentLdt) || listLdt.isEqual(currentLdt) ) &&
                    ( (listLdt.isBefore(ldtIn30Days) || listLdt.isEqual(ldtIn30Days)) ) ){
                aptsForNext30Days.add(a);
            }
        }
            appntTableView.setItems(aptsForNext30Days);

    }

    public void onWeekAppntView(ActionEvent actionEvent) {
        ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
        allAppts = getAllAppointments();
        ObservableList<Appointment> aptsForNext7Days = FXCollections.observableArrayList();

        LocalDateTime currentLdt = LocalDateTime.now();
        LocalDateTime ldtIn7Days = currentLdt.plusDays(7);

        for (Appointment a : allAppts) {
            LocalDateTime listLdt = a.getStartDateTime();

            if ( (listLdt.isAfter(currentLdt) || listLdt.isEqual(currentLdt) ) &&
                    ( (listLdt.isBefore(ldtIn7Days) || listLdt.isEqual(ldtIn7Days)) ) ){
                aptsForNext7Days.add(a);
            }
        }
        appntTableView.setItems(aptsForNext7Days);

    }

    public void onExit(ActionEvent actionEvent) {

        JDBC.closeConnection();
        System.exit(0);
    }
}