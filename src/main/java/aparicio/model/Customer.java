package aparicio.model;


/** This class creates a customer.*/
public class Customer {

    private int customerId;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNum;
    private int divisionId;
    private String country;

    /**This method is used as the constructor for customers.
     * @param  customerId the customer ID
     * @param name the customer name
     * @param address the customer address
     * @param postalCode the customer postal code
     * @param phoneNum the customer phone number
     * @param divisionId the customer division ID
     * @param  country the customer country name
     */
    public Customer(int customerId, String name, String address, String postalCode, String phoneNum, int divisionId, String country) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNum = phoneNum;
        this.divisionId = divisionId;
        this.country = country;
    }

    /**
     * @param customerId the customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @param name the customer name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @param postalCode the postal code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @param phoneNum the phone number to set
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * @param divisionId the division ID to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * @param country the country name to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @return the customer name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @return the phone number
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * @return the division ID
     */
    public int getDivisionId() {
        return divisionId;
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
