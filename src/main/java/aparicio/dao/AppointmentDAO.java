package aparicio.dao;

import aparicio.helper.JDBC;
import aparicio.model.Appointment;
import aparicio.model.Contact;
import aparicio.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;


/** This class is used to Create, Read, Update, or Delete appointments from the database.*/
public abstract class AppointmentDAO {

    /**This method is called when an appointment needs to be created in the database. It takes arguments to create an appointment
     * and saves to the database.
     * @param title the appointment title
     * @param descrip the appointment description
     * @param location the appointment location
     * @param type the appointment type
     * @param startDtTm the appointment start date time
     * @param endDtTm the appointment end date time
     * @param customerId the appointment customer ID
     * @param userId the appointment user ID
     * @param contactId the appointment contact ID
     * */
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

    /**This method is called when a list of all appointments needs to be created. It requests a list of all available
     * appointments in the database.
     * @return a list of all appointments
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, " +
                    "Contact_ID FROM appointments";
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

    /**This method is called when an appointment needs to be updated.
     * @param title the appointment title
     * @param description the appointment description
     * @param location the appointment location
     * @param type the appointment type
     * @param uStartDtTm the appointment start date time
     * @param uEndDtTm the appointment end date time
     * @param customerId the appointment customer ID
     * @param userId the appointment user ID
     * @param contactId the appointment contact ID
     * @param appntId the appointment ID
     */
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

    /**This method is called when a list of appointments needs to be created. It requests a list of all available
     * appointments in the database that match a customer ID, excluding the appointment ID that is passed as an argument.
     * @param customId the customer ID to match
     * @param appntId the appointment ID to exclude
     * @return a list of appointments matching customer ID and excluding appointment ID
     * */
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

    /**This method is called when a list of appointments needs to be created. It requests a list of all available
     * appointments in the database that match a customer ID. It is an overload method from the previous method.
     * @param customId the customer ID to match
     * @return a list of appointments matching customer ID
     * */
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

/**This method is called when a list of appointments needs to be created. It requests a list of all available
 * appointments in the database that match a User ID.
 * @param uId the user ID to match
 * @return a list of appointments matching user ID
 * */
    public static ObservableList<Appointment> getAppntByUserId(int uId) {
        ObservableList<Appointment> appntByUId = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, " +
                    "Contact_ID FROM appointments WHERE User_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, uId);
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
                appntByUId.add(appnt);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return appntByUId;

    }

    /**This method is called when an appointment needs to be deleted from the database.
     * @param appointmentId the appointment ID to match for deletion
     * */
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

    /**This method is used to get a list of types from the appointments table in the database.
     * @return a list of types
     */
    public static ObservableList<String> getAllTypes() {
        ObservableList<String> allTypes = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Type FROM appointments";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String type = rs.getString("Type");
                allTypes.add(type);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return allTypes;
    }

    /**This method is called when a list of appointments needs to be created. It requests a list of all available
     * appointments in the database that match a Contact ID.
     * @param contId the contact ID to match
     * @return a list of appointments matching contact ID
     * */
    public static ObservableList<Appointment> getAppntByContactId(int contId) {
        ObservableList<Appointment> appntByConId = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, " +
                    "Contact_ID FROM appointments WHERE appointments.Contact_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contId);
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
                appntByConId.add(appnt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(appntByConId);
        return appntByConId;
    }

    /**This method is used to get a list of locations from the appointments table in the database.
     * @return a list of locations
     * */
    public static ObservableList<String> getAllLocations() {
        ObservableList<String> allLocations = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Location FROM appointments";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String location = rs.getString("Location");
                allLocations.add(location);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return allLocations;
    }

}
