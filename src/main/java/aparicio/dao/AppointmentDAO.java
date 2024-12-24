package aparicio.dao;

import aparicio.helper.JDBC;
import aparicio.helper.TimeConversion;
import aparicio.model.Appointment;
import aparicio.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.TimeZone;

import static aparicio.helper.TimeConversion.utcToLdt;

public abstract class AppointmentDAO {

    public static void addAppointment(String title, String descrip, String location, String type, LocalDateTime startDtTm,
                                      LocalDateTime endDtTm, int customerId, int userId, int contactId) {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, " +
                    "End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            Timestamp startts = Timestamp.valueOf(startDtTm);
            Timestamp endts = Timestamp.valueOf(endDtTm);

            ps.setString(1, title);
            ps.setString(2, descrip);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startts);
            ps.setTimestamp(6, endts);
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

               //ZonedDateTime localZDT = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
                // Instant localToUtc = localZDT.toInstant();
                // ZonedDateTime z = localToUtc.atZone(TimeZone.getDefault().toZoneId());
               // System.out.println(z);

                Timestamp start = rs.getTimestamp("Start");
                LocalDateTime start1 = start.toLocalDateTime();
                //System.out.println(start1);

                LocalDateTime start2 = TimeConversion.utcToLdt(start1);
                //System.out.println(start2);

                Timestamp end = rs.getTimestamp("End");
                LocalDateTime end1 = end.toLocalDateTime();
                LocalDateTime end2 = TimeConversion.utcToLdt(end1);


                int custId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment appnt = new Appointment(appId, title, descrip, location, type, start2, end2, custId, userId, contactId);
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


    public static ObservableList<Appointment> getAppntByCustID(int customId, int appntId) {
        ObservableList<Appointment> appntList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, " +
                    "Contact_ID FROM appointments WHERE Customer_ID = ? AND Appointment_ID <> ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customId);
            ps.setInt(2,appntId);
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
                appntList.add(appnt);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return appntList;
    }


    public static ObservableList<Appointment> getAppntByCustID(int customId) {
        ObservableList<Appointment> appntList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, " +
                    "Contact_ID FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customId);
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
                appntList.add(appnt);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return appntList;
    }



    public static void deleteAppointment (int appointmentId) {

        try {
            String sql = "Delete from appointments Where Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointmentId);
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
