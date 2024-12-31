package aparicio.controller;

import aparicio.dao.AppointmentDAO;
import aparicio.dao.CustomerDAO;
import aparicio.helper.JDBC;
import aparicio.helper.MonthViewInterface;
import aparicio.helper.WeekViewInterface;
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

/**This class creates the Dashboard screen for the Scheduling Application.
 * Contains all features and buttons and redirects to new screens.*/
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

    /**This method stores the logged user's info to check for upcoming appointments. */
    public static void passLogedUser(User userLogedIn) {
        logedInUser = userLogedIn;
    }

    /**This method checks if the user has an upcoming appointment in the next 15 mins .
     * If it does, a pop-up window will alert the user.*/
    private void setAppointmentAlert() {
        if (!firstTime) {
            return;
        }
        firstTime = false;

        ObservableList<Appointment> apptsByUserList = FXCollections.observableArrayList();
        apptsByUserList = AppointmentDAO.getAppntByUserId(logedInUser.getUserId());

        LocalDateTime current = LocalDateTime.now();
        LocalDateTime in15Min = current.plusMinutes(15);
        boolean appntAlert = false;
        Appointment a = null;

        for (Appointment b : apptsByUserList) {
            LocalDateTime appntLdt = b.getStartDateTime();

            if ( (appntLdt.isAfter(current) && appntLdt.isBefore(in15Min)) || (appntLdt.isEqual(in15Min)) ){
                appntAlert = true;
                a = b;
            }
        }

        if (appntAlert) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("You have an upcoming Appointment. ID: " + a.getAppointmentId() + " Date: "
                    + a.getStartDateTime().toLocalDate() + " Time: " + a.getStartDateTime().toLocalTime());
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention Dialog");
            alert.setContentText("You have No upcoming Appointment.");
            alert.showAndWait();
        }
    }

    /**This method sets the customer and appointment tableviews with data.
     * It calls the data from the mySql database.*/
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

    /**This method is called when the Add customer button is clicked. It redirects the user to the Add Customer form.*/
    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 550);
        stage.setTitle("Add Customer Form");
        stage.setScene(scene);
        stage.show();
    }

    /**This method is called when the Update customer button is clicked. This method reads the customer selected and
     * opens up the Add Customer screen with the customer selected data populated.*/
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

    /** This method is called when the Customer Delete button is clicked.
     * This method deletes the customer and all associated appointments. If no selection is made, will display Alert message.*/
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

    /** This method is called when the Add button for Appointments is clicked. This method opens up the Add Appointment screen.*/
    public void onAddAppnt(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 700);
        stage.setTitle("Add Appointment Form");
        stage.setScene(scene);
        stage.show();
    }

    /**This method is called when the Update Appointment button is clicked. This method reads the appointment selected and
     * opens up the Add Appointment screen with the appointment selected data populated.*/
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

    /** This method is called when the Appointment Delete button is clicked.
     * This method deletes the Appointment. If no selection is made, will display Alert message.*/
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
                alert2.setTitle("Alert Dialog");
                alert2.setContentText("Appointment ID: " + appntIdAsSt + " of type " + type + " has been deleted.");
                alert2.showAndWait();
            }
        }

        appntTableView.setItems(getAllAppointments());
        messageLabel.setText("Message: Appointment ID: " + appntIdAsSt + " of type: " + type + " has been deleted.");
    }

    /**This method is called when the All radio button is clicked. It sets the Appointment tableview with all stored appointments*/
    public void onAllAppntView(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 750);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    /**This method is  lambda #1 and is called when the Month radio button is clicked. It uses a multiple statement
     * lambda expression to filter out appointments in the next 30 days and sets the tableview with these appointments
     * @param actionEvent Month radio button clicked
     */
    public void onMonthAppntView(ActionEvent actionEvent) {
        ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
        allAppts = getAllAppointments();
        ObservableList<Appointment> aptsForNext30Days = FXCollections.observableArrayList();

        LocalDateTime currentLdt = LocalDateTime.now();
        LocalDateTime ldtIn30Days = currentLdt.plusDays(30);

        MonthViewInterface listNext30Days = (L1, L2, ldt1, ldt2) -> {
            for (Appointment a : L1) {
                LocalDateTime listLdt = a.getStartDateTime();

                if ( (listLdt.isAfter(ldt1) || listLdt.isEqual(ldt1) ) &&
                        ( (listLdt.isBefore(ldt2) || listLdt.isEqual(ldt2)) ) ){
                    L2.add(a);
                }
            }
            return L2;
        };
        appntTableView.setItems(listNext30Days.next30Days(allAppts, aptsForNext30Days, currentLdt, ldtIn30Days));
    }


    /**This method is lambda #2 and is called when the Week radio button is clicked. It uses a multiple statement lambda expression to filter out
     * appointments in the next 7 days and sets the tableview with these appointments
     * @param actionEvent Week radio button clicked
     */
    public void onWeekAppntView(ActionEvent actionEvent) {
        ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
        allAppts = getAllAppointments();
        ObservableList<Appointment> aptsForNext7Days = FXCollections.observableArrayList();

        LocalDateTime currentLdt = LocalDateTime.now();
        LocalDateTime ldtIn7Days = currentLdt.plusDays(7);

        WeekViewInterface listNext7Days = (lista, listb, ldta, ldtb) -> {
            for (Appointment a : lista) {
                LocalDateTime listLdt = a.getStartDateTime();

                if ( (listLdt.isAfter(ldta) || listLdt.isEqual(ldta) ) &&
                        ( (listLdt.isBefore(ldtb) || listLdt.isEqual(ldtb)) ) ){
                    listb.add(a);
                }
            }
            return listb;
        };
        appntTableView.setItems(listNext7Days.next7Days(allAppts, aptsForNext7Days, currentLdt, ldtIn7Days));
    }

    /**This method is called when the Run Reports button is clicked. It redirects the user the Reports screen.*/
    public void onRunReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Reports.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 750);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }

    /** This method is called when the Exit button is clicked. This method closes the program.*/
    public void onExit(ActionEvent actionEvent) {

        JDBC.closeConnection();
        System.exit(0);
    }

}