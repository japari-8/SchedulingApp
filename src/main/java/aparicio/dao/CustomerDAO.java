package aparicio.dao;

import aparicio.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import aparicio.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomerDAO {


    public static ObservableList<Customer> getAllCustomerData() {
        ObservableList<Customer> allCustData = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, " +
                    "countries.Country FROM customers, countries, first_level_divisions WHERE " +
                    "customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int custId = rs.getInt("Customer_ID");
                String custName = rs.getString("Customer_Name");
                String custAddress = rs.getString("Address");
                String custPostCode = rs.getString("Postal_Code");
                String custPhone = rs.getString("Phone");
                int custDivId = rs.getInt("Division_ID");
                String custCountry = rs.getString("Country");

                Customer c = new Customer(custId, custName, custAddress, custPostCode, custPhone, custDivId, custCountry);
                allCustData.add(c);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustData;
    }


    public static void addCust(String fullName, String address, String postalCode, String phoneNum, int divisionId) {

        try {
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
        catch (SQLException e) {
                e.printStackTrace();
        }
    }

    public static void updateCust(int customerID, String fullName, String address, String postalCode, String phoneNum,
                                 int divisionId) {
        try {
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

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteCustomerData(int customerId) {

        try {
           /* String sql = "Delete from appointments Where Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.executeUpdate();
            */
            String sql1 = "Delete from customers Where Customer_ID = ?";
            PreparedStatement ps1 = JDBC.connection.prepareStatement(sql1);
            ps1.setInt(1, customerId);
            ps1.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


}


