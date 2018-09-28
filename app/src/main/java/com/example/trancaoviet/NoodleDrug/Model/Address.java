package com.example.trancaoviet.NoodleDrug.Model;

public class Address {
    private String addressName;
    private String Province;
    protected String Distric;
    private String Ward;
    private String Street;

    public Address() {
    }

    public Address(String addressName, String province, String distric, String ward, String street) {
        this.addressName = addressName;
        Province = province;
        Distric = distric;
        Ward = ward;
        Street = street;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getDistric() {
        return Distric;
    }

    public void setDistric(String distric) {
        Distric = distric;
    }

    public String getWard() {
        return Ward;
    }

    public void setWard(String ward) {
        Ward = ward;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }
}
