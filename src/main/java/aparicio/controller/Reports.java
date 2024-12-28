package aparicio.controller;

import aparicio.dao.AppointmentDAO;
import aparicio.model.Appointment;
import aparicio.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;

public class Reports implements Initializable {


    public ComboBox monthCombo;
    public ComboBox typeCombo;
    public TextField numOfAppnts;
    public ComboBox contactCombo;
    public TableView appntTableView2;
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
    public ComboBox locatCombo;
    public TextField numLocAppnts;
    public ObservableList<Month> months = FXCollections.observableArrayList(Month.JANUARY, Month.FEBRUARY, Month.MARCH,
            Month.APRIL, Month.MAY, Month.JUNE, Month.JULY, Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER,
            Month.DECEMBER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        monthCombo.setItems(months);
        typeCombo.setItems(AppointmentDAO.getAllTypes());
        contactCombo.setItems(AppointmentDAO.getAllContacts());

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

        locatCombo.setItems(AppointmentDAO.getAllLocations());

    }


    public void runReport1(ActionEvent actionEvent) {

        Integer counter = 0;
        Month monthSel = (Month) monthCombo.getValue();
        String typeSel = (String) typeCombo.getValue();

        ObservableList <Appointment> appntList = FXCollections.observableArrayList();
        appntList = AppointmentDAO.getAllAppointments();

        for (Appointment c : appntList) {
            Month month = c.getStartDateTime().getMonth();
            String type = c.getType();

            if (monthSel.equals(month) && typeSel.equals(type)) {
                counter++;
            }
        }
        numOfAppnts.setText(counter.toString());
    }

    public void runReport2(ActionEvent actionEvent) {

        Contact con = (Contact)contactCombo.getValue();
        int conId = con.getContactId();

        ObservableList<Appointment> appntByCon = FXCollections.observableArrayList();
        appntByCon = AppointmentDAO.getAppntByContactId(conId);

        appntTableView2.setItems(appntByCon);

    }


    public void runReport3(ActionEvent actionEvent) {

        Integer counter2 = 0;
        String locationSel = (String) locatCombo.getValue();

        ObservableList <Appointment> appntList = FXCollections.observableArrayList();
        appntList = AppointmentDAO.getAllAppointments();

        for (Appointment d : appntList) {
            String location = d.getLocation();

            if (locationSel.equals(location) ) {
                counter2++;
            }
        }
        numLocAppnts.setText(counter2.toString());
    }


    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aparicio/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 750);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}
