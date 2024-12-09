package model;

public class Customer {
    private int customerId;
    private String name;
    private String address;
    private int postalCode;
    private int phoneNum;
    private int divisionId;

    public Customer(int customerId, String name, String address, int postalCode, int phoneNum, int divisionId) {
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

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public void setPhoneNum(int phoneNum) {
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

    public int getPostalCode() {
        return postalCode;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public int getDivisionId() {
        return divisionId;
    }

}
