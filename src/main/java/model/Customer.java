package model;

public class Customer {
    private int customerId;
    private String name;
    private String address;
    private int postalCode;
    private int phoneNum;

    public Customer(int customerId, String name, String address, int postalCode, int phoneNum) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNum = phoneNum;
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

}
