package aparicio.controller;

import aparicio.dao.AppointmentDAO;
import aparicio.dao.ContactDAO;
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
import java.time.*;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class UpdateAppointment implements Initializable {


    public DatePicker date2;
    public ComboBox startTimeCombo2;
    public ComboBox endTimeCombo2;
    public TextField updateAppntTittle;
    public TextField updateAppntDescrip;
    public TextField updateAppntType;
    public TextField updateAppntLocation;
    public ComboBox custIdCombo2;
    public ComboBox userIdCombo2;
    public ComboBox contactCombo2;
    public TextField appntId;
    public static Appointment selAppointment = null;

    public static void passSelAppnt(Appointment selAppnt) {
        selAppointment = selAppnt;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId localZoneId = ZoneId.systemDefault();

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
            startTimeCombo2.getItems().add(lt);
            lt = lt.plusHours(1);
        }

        LocalTime easternEndTimeStart = LocalTime.of(9,0);
        LocalTime easternEndTimeEnd = LocalTime.of(22,0);

        LocalDateTime ldta = LocalDateTime.of(LocalDate.now(), easternEndTimeStart);
        ZonedDateTime zdta = ldta.atZone(easternZoneId).withZoneSameInstant(localZoneId);
        LocalTime lta = zdta.toLocalTime();

        LocalDateTime ldtb = LocalDateTime.of(LocalDate.now(), easternEndTimeEnd);
        ZonedDateTime zdtb = ldtb.atZone(easternZoneId).withZoneSameInstant(localZoneId);
        LocalTime ltb = zdtb.toLocalTime();

        while (lta.isBefore(ltb.plusSeconds(1))) {
            endTimeCombo2.getItems().add(lta);
            lta = lta.plusHours(1);
        }
        LocalDate ld = selAppointment.getStartDateTime().toLocalDate();
        LocalTime startT = selAppointment.getStartDateTime().toLocalTime();
        LocalTime endT = selAppointment.getEndDateTime().toLocalTime();
        date2.setValue(ld);
        startTimeCombo2.setValue(startT);
        endTimeCombo2.setValue(endT);

        custIdCombo2.setItems(AppointmentDAO.getAllCustomerIDs());

        ObservableList<Integer> userList = FXCollections.observableArrayList(1, 2);
        userIdCombo2.setItems(userList);

        contactCombo2.setItems(AppointmentDAO.getAllContacts());

        updateAppntTittle.setText(selAppointment.getTitle());
        updateAppntDescrip.setText(selAppointment.getDescription());
        updateAppntType.setText(selAppointment.getType());
        updateAppntLocation.setText(selAppointment.getLocation());
        appntId.setText(Integer.toString(selAppointment.getAppointmentId()));
        custIdCombo2.setValue(selAppointment.getCustomerId());
        userIdCombo2.setValue(selAppointment.getUserId());

        contactCombo2.setValue(ContactDAO.getContactById(selAppointment.getContactId()));
    }

    
    public void onSaveUpdateAppnt(ActionEvent actionEvent) throws IOException {

        LocalDate uDate = date2.getValue();

        String ust = startTimeCombo2.getValue().toString();
        LocalTime uStartTime = LocalTime.parse(ust);
        LocalDateTime usdt = LocalDateTime.of(uDate, uStartTime);

        String uet = endTimeCombo2.getValue().toString();
        LocalTime uEndTime = LocalTime.parse(uet);
        LocalDateTime uedt = LocalDateTime.of(uDate, uEndTime);

        String upTitle = updateAppntTittle.getText();
        String upDescription = updateAppntDescrip.getText();
        String upType = updateAppntType.getText();
        String upLocation = updateAppntLocation.getText();

        Integer uCustomer = (Integer) custIdCombo2.getSelectionModel().getSelectedItem();
        int uCustId = uCustomer;

        Integer uUser = (Integer) userIdCombo2.getSelectionModel().getSelectedItem();
        int uUserId = uUser;

        Contact uContact = (Contact)contactCombo2.getValue();
        int uContactId = uContact.getContactId();

        int uAppntId = Integer.parseInt(appntId.getText());

        ObservableList<Appointment> aptList = FXCollections.observableArrayList();
        aptList = AppointmentDAO.getAppntByCustID(uCustId, uAppntId);

        boolean overlaps = false;
        for (Appointment ap : aptList) {
            LocalDateTime ovSt = ap.getStartDateTime();
            LocalDateTime ovEt = ap.getEndDateTime();

            if ( (ovSt.isAfter(usdt) || ovSt.isEqual(usdt) ) && ( ovSt.isBefore(uedt)) ) {
                overlaps = true;
            }
            else if ( (ovEt.isAfter(usdt) ) && ( ovEt.isBefore(uedt) || ovEt.isEqual(uedt)) ) {
                overlaps = true;
            }
            else if ( (ovEt.isBefore(usdt) || ovEt.isEqual(usdt) ) && ( ovSt.isAfter(uedt) || ovSt.isEqual(uedt) ) ) {
                overlaps =true;
            }

        }

        if (overlaps) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("The times chosen overlap with another appointment for this customer. Please select a different appointment time.");
            alert.showAndWait();
        }

        else {
            AppointmentDAO.updateAppointment(upTitle, upDescription, upLocation, upType, usdt, uedt, uCustId, uUserId, uContactId, uAppntId);

            Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 750);
            stage.setTitle("Dashboard");
            stage.setScene(scene);
            stage.show();
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