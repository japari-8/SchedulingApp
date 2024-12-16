package aparicio.model;

public class FirstLevelDivision {
    private int divisionId;
    private String division;

    public FirstLevelDivision(int divisionId, String division) {
        this.divisionId = divisionId;
        this.division = division;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public void setDivision(String division) {
        this.division = division;
    }


    public int getDivisionId() {
        return divisionId;
    }

    public String getDivision() {
        return division;
    }

    @Override
    public String toString() {
        return division;
    }
}
