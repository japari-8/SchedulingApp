package aparicio.model;

public class Customer {

    private int customerId;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNum;
    private int divisionId;

    public Customer(int customerId, String name, String address, String postalCode, String phoneNum, int divisionId) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNum = phoneNum;
        this.divisionId = divisionId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }


    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public int getDivisionId() {
        return divisionId;
    }

}
