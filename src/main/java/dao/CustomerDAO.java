package dao;

import helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class CustomerDAO {

    public static void insertCust(String fullName, String address, String postalCode, String phoneNum, int divisionId)
            throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                    "Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, fullName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNum);
        ps.setString(5, null);
        ps.setString(6, null);
        ps.setString(7, null);
        ps.setString(8, null);
        ps.setInt(9, divisionId);
        ps.executeUpdate();
    }

    public static int updateCust(int customerID, String fullName, String address, String postalCode, String phoneNum,
                                 int divisionId) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, " +
                    "Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, fullName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNum);
        ps.setString(5, null);
        ps.setString(6, null);
        ps.setString(7, null);
        ps.setString(8, null);
        ps.setInt(9, divisionId);
        ps.setInt(10, customerID);

        ps.executeUpdate();

        int rowsAffected =ps.executeUpdate();
        return rowsAffected;
    }


}


