package aparicio.dao;

import aparicio.helper.JDBC;
import aparicio.model.Appointment;
import aparicio.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public abstract class AppointmentDAO {

    public static void addAppointment(String title, String descrip, String location, String type, Timestamp startDtTm,
                                      Timestamp endDtTm, int customerId, int userId, int contactId) {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, " +
                    "End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, descrip);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startDtTm);
            ps.setTimestamp(6, endDtTm);
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String descrip = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");

                Timestamp start = rs.getTimestamp("Start");
                LocalDateTime start1 = start.toLocalDateTime();
                Timestamp end = rs.getTimestamp("End");
                LocalDateTime end1 = end.toLocalDateTime();
                int custId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment appnt = new Appointment(appId, title, descrip, location, type, start1, end1, custId, userId, contactId);
                allAppointments.add(appnt);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }


    public static void updateAppointment (String title, String description, String location, String type,
                                          LocalDateTime uStartDtTm, LocalDateTime uEndDtTm, int customerId, int userId,
                                          int contactId, int appntId) {

        Timestamp startts = Timestamp.valueOf(uStartDtTm);
        Timestamp endts = Timestamp.valueOf(uEndDtTm);

        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, " +
                         "End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startts);
            ps.setTimestamp(6, endts);
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);
            ps.setInt(10, appntId);
            ps.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static ObservableList<Integer> getAllCustomerIDs() {
        ObservableList<Integer> allCustomerIDs = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Customer_ID FROM customers";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int cusId = rs.getInt("Customer_ID");
                allCustomerIDs.add(cusId);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomerIDs;
    }

    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Contact_ID, Contact_Name FROM contacts";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactNm = rs.getString("Contact_Name");
                Contact contact = new Contact(contactId, contactNm);
                allContacts.add(contact);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return allContacts;
    }


}
