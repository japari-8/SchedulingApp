package aparicio.model;


/** This class creates a country.*/
public class Country {
    private int countryId;
    private String country;

    /**This method is used as the constructor for country.
     * @param countryId the country ID
     * @param country the country name
     */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * @param countryId the country ID to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * @param country the country name to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @return the country name
     */
    public String getCountry() {
        return country;
    }

    /**This method overrides the toString method.
     * @return the country name
     */
    @Override
    public String toString() {
        return country;
    }
}

