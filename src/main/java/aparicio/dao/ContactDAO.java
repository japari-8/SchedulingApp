package aparicio.dao;

import aparicio.helper.JDBC;
import aparicio.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is used to Create, Read, Update, or Delete Contacts from the database.*/
public class ContactDAO {

    /**This method is called when a list of all contacts needs to be created. It requests a list of all available
     * contacts in the database.
     * @return a list of all contacts
     * */
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

    /**This method is called when a contact needs to be returned. It requests the contact associated with a contact ID.
     * @param contactId the contact ID to match
     * @return a contact matching contact ID
     */
    public static Contact getContactById(int contactId) {
        Contact contact = null;

        try {
            String sql = "SELECT Contact_ID, Contact_Name FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactId1 = rs.getInt("Contact_ID");
                String contactNm = rs.getString("Contact_Name");
                contact = new Contact(contactId, contactNm);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return contact;
    }
}
