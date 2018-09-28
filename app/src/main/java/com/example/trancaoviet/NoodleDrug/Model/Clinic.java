package com.example.trancaoviet.NoodleDrug.Model;

import android.graphics.Bitmap;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

public class Clinic implements Serializable {
    private int Id;
    private String Name;
    private LatLng LatLng;
    private String Address;
    private ArrayList<Bitmap> ImageList;
    private String CalenderWork;
    private ArrayList<String> Faculty;

    public Clinic() {
    }

    public Clinic(String name, LatLng latLng, String address, ArrayList<Bitmap> imageList, String calenderWork, ArrayList<String> faculty) {
        Name = name;
        LatLng = latLng;
        Address = address;
        ImageList = imageList;
        CalenderWork = calenderWork;
        Faculty = faculty;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public com.google.android.gms.maps.model.LatLng getLatLng() {
        return LatLng;
    }

    public void setLatLng(com.google.android.gms.maps.model.LatLng latLng) {
        LatLng = latLng;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public ArrayList<Bitmap> getImageList() {
        return ImageList;
    }

    public void setImageList(ArrayList<Bitmap> imageList) {
        ImageList = imageList;
    }

    public String getCalenderWork() {
        return CalenderWork;
    }

    public void setCalenderWork(String calenderWork) {
        CalenderWork = calenderWork;
    }

    public ArrayList<String> getFaculty() {
        return Faculty;
    }

    public void setFaculty(ArrayList<String> faculty) {
        Faculty = faculty;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
