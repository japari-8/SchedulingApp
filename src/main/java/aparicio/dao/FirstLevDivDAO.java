package aparicio.dao;

import aparicio.helper.JDBC;
import aparicio.model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import aparicio.model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevDivDAO {

    public static ObservableList<FirstLevelDivision> getUsFirstLevDiv() {

        ObservableList<FirstLevelDivision> usStates = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Division_ID, Division FROM first_level_divisions WHERE Country_ID = 1";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divId = rs.getInt("Division_ID");
                String div = rs.getString("Division");

                FirstLevelDivision div1 = new FirstLevelDivision(divId, div);
                usStates.add(div1);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return usStates;
    }

    public static ObservableList<FirstLevelDivision> getUkFirstLevDiv() {

        ObservableList<FirstLevelDivision> ukRegions = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Division_ID, Division FROM first_level_divisions WHERE Country_ID = 2";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divId = rs.getInt("Division_ID");
                String div = rs.getString("Division");

                FirstLevelDivision div2 = new FirstLevelDivision(divId, div);
                ukRegions.add(div2);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return ukRegions;
    }

    public static ObservableList<FirstLevelDivision> getCanFirstLevDiv() {

        ObservableList<FirstLevelDivision> canProvinces = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Division_ID, Division FROM first_level_divisions WHERE Country_ID = 3";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divId = rs.getInt("Division_ID");
                String div = rs.getString("Division");

                FirstLevelDivision div1 = new FirstLevelDivision(divId, div);
                canProvinces.add(div1);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return canProvinces;
    }


    public static FirstLevelDivision getDivision (int divId) {
        FirstLevelDivision division = null;
        try {
            String sql = "SELECT Division_ID, Division FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, divId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int dId = rs.getInt("Division_ID");
                String div = rs.getString("Division");
                division = new FirstLevelDivision(dId, div);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return division;
    }


    public static FirstLevelDivision getDivisionId (String StateProv) {
        FirstLevelDivision FLevdiv = null;
        try {
            String sql = "SELECT Division_ID, Division FROM first_level_divisions WHERE Division = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, StateProv);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int dId = rs.getInt("Division_ID");
                String stateP = rs.getString("Division");
                FLevdiv = new FirstLevelDivision(dId, stateP);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return FLevdiv;
    }

}