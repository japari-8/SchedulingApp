package aparicio.dao;

import aparicio.helper.JDBC;
import aparicio.model.Contact;
import aparicio.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDAO {

    public static ObservableList<User> getAllUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            String sql = "SELECT User_ID, User_Name, Password FROM users";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");

                User u = new User(userId, userName, password);
                users.add(u);
            }
        }
        catch (SQLException e) {
                e.printStackTrace();
            }
        return users;
    }

    public static User getUserLogedIn(String uName, String pWord) {
        User user = null;

        try {
            String sql = "SELECT User_ID, User_Name, Password FROM users WHERE User_Name = ? AND Password = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, uName);
            ps.setString(2, pWord);
            ResultSet rs = ps.executeQuery();

            int userId = rs.getInt("User_ID");
            String userN = rs.getString("User_Name");
            String passW = rs.getString("Password");

            user = new User(userId, userN, passW);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


}
