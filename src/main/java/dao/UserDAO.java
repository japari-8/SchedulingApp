package dao;

import helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class UserDAO {

    //Use this method to test insert method. Delete after testing.
    public static int insert(String userName, String password) throws SQLException {
        String sql = "INSERT INTO USERS (User_Name, Password) VALUES (?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }

}
