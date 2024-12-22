package aparicio.controller;

import aparicio.dao.AppointmentDAO;
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
    public final ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LocalTime easternStartTimeStart = LocalTime.of(8,0);
        LocalTime easternStartTimeEnd = LocalTime.of(21,0);

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


        LocalTime easternEndTimeStart = LocalTime.of(9,0);
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

        custIdCombo.setItems(AppointmentDAO.getAllCustomerIDs());

        ObservableList<Integer> userList = FXCollections.observableArrayList(1, 2);
        userIdCombo.setItems(userList);

       addContactCombo.setItems(AppointmentDAO.getAllContacts());

    }


    public void onSaveAddAppnt(ActionEvent actionEvent) {

        try {
            LocalDate dateChosen = date.getValue();

            String st = startTimeCombo.getValue().toString();
            LocalTime startTime = LocalTime.parse(st);
            LocalDateTime sdt = LocalDateTime.of(dateChosen, startTime);

            //ZonedDateTime zdt = ZonedDateTime.of(dateChosen, startTime, localZoneId);
            //Instant zdtInstant = zdt.toInstant();

            Timestamp finalsdt = Timestamp.valueOf(sdt);

            //Instant tsInstant = finalsdt.toInstant();


            String et = endTimeCombo1.getValue().toString();
            LocalTime endTime = LocalTime.parse(et);
            LocalDateTime edt = LocalDateTime.of(dateChosen, endTime);
            Timestamp finaledt = Timestamp.valueOf(edt);

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

            AppointmentDAO.addAppointment(title, descrip, location, type, finalsdt, finaledt, custId, userId, aContactId);
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please make a selection in every field");
            alert.showAndWait();
        }

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