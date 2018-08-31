package com.example.trancaoviet.NoodleDrug.Object;

import java.util.ArrayList;

public class ExaminationMedicalPackage {

    private String Name;
    private String Description;
    private int Price;
    private ArrayList<Pharmacy> PharmacyList;

    public ExaminationMedicalPackage() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public ArrayList<Pharmacy> getPharmacyList() {
        return PharmacyList;
    }

    public void setPharmacyList(ArrayList<Pharmacy> pharmacyList) {
        PharmacyList = pharmacyList;
    }
}
