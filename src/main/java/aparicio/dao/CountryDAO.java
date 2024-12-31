package aparicio.dao;

import aparicio.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import aparicio.model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/** This class is used to only Read countries from the database.*/
public abstract class CountryDAO {

    /**This method is called when a list of all countries needs to be created. It requests a list of all available
     * countries in the database.
     * @return a list of all countries
     * */
    public static ObservableList<Country> getAllCountries() {

        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Country_ID, Country FROM countries";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");

                Country con = new Country(countryId, country);
                allCountries.add(con);
            }

        }

        catch (SQLException e){
            e.printStackTrace();
        }
        return allCountries;
    }
}
