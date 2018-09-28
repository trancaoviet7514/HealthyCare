package com.example.trancaoviet.NoodleDrug.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Service implements Serializable {

    private int Id;
    private String Name;
    private String Description;
    private int Price;
    private ArrayList<Integer> PharmacyList = new ArrayList<>();

    public Service() {
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

    public ArrayList<Integer> getPharmacyList() {
        return PharmacyList;
    }

    public void setPharmacyList(ArrayList<Integer> pharmacyList) {
        PharmacyList = pharmacyList;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
