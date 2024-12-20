package aparicio.controller;

import aparicio.dao.AppointmentDAO;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LocalTime startTimeStart = LocalTime.of(8,0);
        LocalTime startTimeEnd = LocalTime.of(21,0);

        while (startTimeStart.isBefore(startTimeEnd.plusSeconds(1))) {
            startTimeCombo.getItems().add(startTimeStart);
            startTimeStart = startTimeStart.plusHours(1);
        }
        startTimeCombo.getSelectionModel().select(LocalTime.of(8,0));

        LocalTime endTimeStart = LocalTime.of(9,0);
        LocalTime endTimeEnd = LocalTime.of(22,0);

        while (endTimeStart.isBefore(endTimeEnd.plusSeconds(1))) {
            endTimeCombo1.getItems().add(endTimeStart);
            endTimeStart = endTimeStart.plusHours(1);
        }
        endTimeCombo1.getSelectionModel().select(LocalTime.of(9,0));

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
            Timestamp finalsdt = Timestamp.valueOf(sdt);

            String et = endTimeCombo1.getValue().toString();
            LocalTime endTime = LocalTime.parse(et);
            LocalDateTime edt = LocalDateTime.of(dateChosen, endTime);
            Timestamp finaledt = Timestamp.valueOf(edt);

            String title = addAppntTittle.getText();
            String descrip = addAppntDescrip.getText();
            String type = addAppntType.getText();
            String location = addAppntLocation.getText();
            int custId = custIdCombo.getSelectionModel().getSelectedItem().hashCode();
            int userId = userIdCombo.getSelectionModel().getSelectedItem().hashCode();

            String contactId = addContactCombo.getValue().toString();
            int contactID = 0;
            if (contactId.contains("Anika Costa")) {
                contactID = 1;
            } else if (contactId.contains("Daniel Garcia")) {
                contactID = 2;
            } else if (contactId.contains("Li Lee")) {
                contactID = 3;
            }
            System.out.println(contactID);
            AppointmentDAO.addAppointment(title, descrip, location, type, finalsdt, finaledt, custId, userId, contactID);
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