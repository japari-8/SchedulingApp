package aparicio.dao;

import aparicio.helper.JDBC;
import aparicio.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAO {

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
