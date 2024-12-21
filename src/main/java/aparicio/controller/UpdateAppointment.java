package aparicio.controller;

import aparicio.dao.AppointmentDAO;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

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

        LocalTime startTimeStart = LocalTime.of(8,0);
        LocalTime startTimeEnd = LocalTime.of(21,0);

        while (startTimeStart.isBefore(startTimeEnd.plusSeconds(1))) {
            startTimeCombo2.getItems().add(startTimeStart);
            startTimeStart = startTimeStart.plusHours(1);
        }

        LocalTime endTimeStart = LocalTime.of(9,0);
        LocalTime endTimeEnd = LocalTime.of(22,0);

        while (endTimeStart.isBefore(endTimeEnd.plusSeconds(1))) {
            endTimeCombo2.getItems().add(endTimeStart);
            endTimeStart = endTimeStart.plusHours(1);
        }

        custIdCombo2.setItems(AppointmentDAO.getAllCustomerIDs());

        ObservableList<Integer> userList = FXCollections.observableArrayList(1, 2);
        userIdCombo2.setItems(userList);

        contactCombo2.setItems(AppointmentDAO.getAllContacts());

        updateAppntTittle.setText(selAppointment.getTitle());
        updateAppntDescrip.setText(selAppointment.getDescription());
        updateAppntType.setText(selAppointment.getType());
        updateAppntLocation.setText(selAppointment.getLocation());
        appntId.setText(Integer.toString(selAppointment.getAppointmentId()));

        LocalDate ld = selAppointment.getStartDateTime().toLocalDate();
        date2.setValue(ld);

        LocalTime lts = selAppointment.getStartDateTime().toLocalTime();
        startTimeCombo2.setValue(lts);

        LocalTime lte = selAppointment.getEndDateTime().toLocalTime();
        endTimeCombo2.setValue(lte);
        custIdCombo2.setValue(selAppointment.getCustomerId());
        userIdCombo2.setValue(selAppointment.getUserId());
        contactCombo2.setValue(selAppointment.getContactId());


    }

    
    public void onSaveUpdateAppnt(ActionEvent actionEvent) {

        String upTitle = updateAppntTittle.getText();
        String upDescription = updateAppntDescrip.getText();
        String upType = updateAppntType.getText();
        String upLocation = updateAppntLocation.getText();

        LocalDate uDate = date2.getValue();

        String ust = startTimeCombo2.getValue().toString();
        LocalTime uStartTime = LocalTime.parse(ust);
        LocalDateTime usdt = LocalDateTime.of(uDate, uStartTime);

        String uet = endTimeCombo2.getValue().toString();
        LocalTime uEndTime = LocalTime.parse(uet);
        LocalDateTime uedt = LocalDateTime.of(uDate, uEndTime);

        Integer uCustomer = (Integer) custIdCombo2.getSelectionModel().getSelectedItem();
        int uCustId = uCustomer;

        Integer uUser = (Integer) userIdCombo2.getSelectionModel().getSelectedItem();
        int uUserId = uUser;

        Contact uContact = (Contact)contactCombo2.getValue();
        int uContactId = uContact.getContactId();

        int uAppntId = Integer.parseInt(appntId.getText());

        AppointmentDAO.updateAppointment(upTitle, upDescription, upLocation, upType, usdt, uedt, uCustId, uUserId, uContactId, uAppntId);

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