package aparicio.model;


/** This class creates a first level division.*/
public class FirstLevelDivision {
    private int divisionId;
    private String division;

    /**This method is used as the constructor for first level divisions.
     * @param divisionId the division ID
     * @param division the division name
     */
    public FirstLevelDivision(int divisionId, String division) {
        this.divisionId = divisionId;
        this.division = division;
    }

    /**
     * @param divisionId the division ID to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * @param division the division name to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @return the division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**This method overrides the hashCode method.
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        return divisionId;
    }

    /**
     * @return the division name
     */
    public String getDivision() {
        return division;
    }

    /**This method overrides the toString method.
     * @return the division name
     */
    @Override
    public String toString() {
        return division;
    }
}
