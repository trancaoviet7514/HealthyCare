package com.example.trancaoviet.NoodleDrug.Object;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Pharmacy {
    private String Name;
    private LatLng Latlng;
    private ArrayList<Integer> DrugIDList;
    private String Address;

    public Pharmacy() {
    }

    public Pharmacy(String name, String address) {
        Name = name;
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public LatLng getLatlng() {
        return Latlng;
    }

    public void setLatlng(LatLng latlng) {
        Latlng = latlng;
    }

    public ArrayList<Integer> getDrugIDList() {
        return DrugIDList;
    }

    public void setDrugIDList(ArrayList<Integer> drugIDList) {
        DrugIDList = drugIDList;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
