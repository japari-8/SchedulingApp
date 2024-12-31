package aparicio.controller;

import aparicio.dao.AppointmentDAO;
import aparicio.dao.ContactDAO;
import aparicio.dao.CustomerDAO;
import aparicio.model.Appointment;
import aparicio.model.Contact;
import aparicio.model.Customer;
import aparicio.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static java.time.LocalDate.now;

/** This class adds an appointment to the database.*/
public class AddAppointment implements Initializable {

    public DatePicker date;
    public ComboBox startTimeCombo;
    public ComboBox endTimeCombo1;
    public TextField addAppntTittle;
    public TextField addAppntDescrip;
    public TextField addAppntType;
    public TextField addAppntLocation;
    public ComboBox custIdCombo;
    public ComboBox userIdCombo;
    public ComboBox addContactCombo;


    /**This method initializes all the combo boxes. It sets the appointment start and end time options to 1 hour increments.
     * These times are translated to local hours from the eastern time operational hours of 8am to 10pm. Customer, User,
     * and Contact combo boxes are also initiated*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId localZoneId = ZoneId.systemDefault();

        LocalTime easternStartTimeStart = LocalTime.of(8,0);
        LocalTime easternStartTimeEnd = LocalTime.of(20,0);

        ZoneId easternZoneId = ZoneId.of("America/New_York");

        LocalDateTime ldt = LocalDateTime.of(LocalDate.now(), easternStartTimeStart);
        ZonedDateTime zdt = ldt.atZone(easternZoneId).withZoneSameInstant(localZoneId);
        LocalTime lt = zdt.toLocalTime();

        LocalDateTime ldt2 = LocalDateTime.of(LocalDate.now(), easternStartTimeEnd);
        ZonedDateTime zdt2 = ldt2.atZone(easternZoneId).withZoneSameInstant(localZoneId);
        LocalTime lt2 = zdt2.toLocalTime();

        while (lt.isBefore(lt2.plusSeconds(1))) {
            startTimeCombo.getItems().add(lt);
            lt = lt.plusHours(1);
        }
        startTimeCombo.getSelectionModel().selectFirst();


        LocalTime easternEndTimeStart = LocalTime.of(10,0);
        LocalTime easternEndTimeEnd = LocalTime.of(22,0);

        LocalDateTime ldta = LocalDateTime.of(LocalDate.now(), easternEndTimeStart);
        ZonedDateTime zdta = ldta.atZone(easternZoneId).withZoneSameInstant(localZoneId);
        LocalTime lta = zdta.toLocalTime();

        LocalDateTime ldtb = LocalDateTime.of(LocalDate.now(), easternEndTimeEnd);
        ZonedDateTime zdtb = ldtb.atZone(easternZoneId).withZoneSameInstant(localZoneId);
        LocalTime ltb = zdtb.toLocalTime();

        while (lta.isBefore(ltb.plusSeconds(1))) {
            endTimeCombo1.getItems().add(lta);
            lta = lta.plusHours(1);
        }
        endTimeCombo1.getSelectionModel().selectFirst();

        custIdCombo.setItems(CustomerDAO.getAllCustomerIDs());

        ObservableList<Integer> userList = FXCollections.observableArrayList(1, 2);
        userIdCombo.setItems(userList);

       addContactCombo.setItems(ContactDAO.getAllContacts());

    }

    /**This method is called when the Save button is clicked. It saves the appointment data, has validation checks for
     * empty fields. Also alerts the user if the appointment overlaps with another appointment.*/
    public void onSaveAddAppnt(ActionEvent actionEvent) throws IOException {

        try {
            LocalDate dateChosen = date.getValue();

            String st = startTimeCombo.getValue().toString();
            LocalTime startTime = LocalTime.parse(st);
            LocalDateTime sdt = LocalDateTime.of(dateChosen, startTime);

            String et = endTimeCombo1.getValue().toString();
            LocalTime endTime = LocalTime.parse(et);
            LocalDateTime edt = LocalDateTime.of(dateChosen, endTime);

            String title = addAppntTittle.getText();
            String descrip = addAppntDescrip.getText();
            String type = addAppntType.getText();
            String location = addAppntLocation.getText();

            Integer aCustomer = (Integer) custIdCombo.getSelectionModel().getSelectedItem();
            int custId = aCustomer;

            Integer aUser = (Integer) userIdCombo.getSelectionModel().getSelectedItem();
            int userId = aUser;

            Contact aContact = (Contact) addContactCombo.getValue();
            int aContactId = aContact.getContactId();

            ObservableList<Appointment> aptList = FXCollections.observableArrayList();
            aptList = AppointmentDAO.getAppntByCustID(aCustomer);

            boolean overlaps = false;
            for (Appointment ap : aptList) {
                LocalDateTime ovSt = ap.getStartDateTime();
                LocalDateTime ovEt = ap.getEndDateTime();

                if ( (ovSt.isAfter(sdt) || ovSt.isEqual(sdt) ) && ( ovEt.isBefore(edt) || ovEt.isEqual(edt)) ) {
                    overlaps = true;
                }
                else if ( (ovSt.isBefore(sdt) ) && ( ovEt.isBefore(edt) || ovEt.isEqual(edt)) ) {
                    overlaps = true;
                }
                else if ( (ovSt.isAfter(sdt) || ovSt.isEqual(sdt) ) && ovEt.isAfter(edt) ) {
                    overlaps = true;
                }
                else if ( (ovSt.isBefore(sdt) || ovSt.isEqual(sdt) ) && ( ovEt.isAfter(edt) || ovEt.isEqual(edt)) ) {
                    overlaps =true;
                }

            }

            if (overlaps) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("The times chosen overlap with another appointment for this customer. Please select a different appointment time.");
                alert.showAndWait();
            } else {

                AppointmentDAO.addAppointment(title, descrip, location, type, sdt, edt, custId, userId, aContactId);

                Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 750);
                stage.setTitle("Dashboard");
                stage.setScene(scene);
                stage.show();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please make a selection in every field");
            alert.showAndWait();
        }
    }

    /**This method is called when the Cancel button is clicked. It cancels the
     * add appointment request and redirects to the Dashboard screen.*/
    public void backToDashboard(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 750);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}