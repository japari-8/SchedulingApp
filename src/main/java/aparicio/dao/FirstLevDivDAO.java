package aparicio.dao;

import aparicio.helper.JDBC;
import aparicio.model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import aparicio.model.Country;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/** This class is used to Create, Read, Update, or Delete first level divisions from the database.*/
public class FirstLevDivDAO {

    /**This method is called when a list of US divisions needs to be created.
     * @return a list of US divisions
     */
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

    /**This method is called when a list of UK divisions needs to be created.
     * @return a list of UK divisions
     */
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

    /**This method is called when a list of Canadian divisions needs to be created.
     * @return a list of Canadian divisions
     */
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

    /**This method is used to request the name of a division associated with a division ID.
     * @param divId the division ID to match
     * @return a division matching tie division ID
     */
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

}