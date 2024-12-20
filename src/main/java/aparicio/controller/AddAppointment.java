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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable {


    public DatePicker date;
    public TextField addAppntTittle;
    public TextField addAppntDescrip;
    public TextField addAppntType;
    public TextField addAppntLocation;
    public ComboBox custIdCombo;
    public ComboBox userIdCombo;
    public ComboBox addContactCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custIdCombo.setItems(AppointmentDAO.getAllCustomerIDs());

        ObservableList<Integer> userList = FXCollections.observableArrayList(1, 2);
        userIdCombo.setItems(userList);

        addContactCombo.setItems(AppointmentDAO.getAllContacts());

    }

    

    public void onSaveAddAppnt(ActionEvent actionEvent) {
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