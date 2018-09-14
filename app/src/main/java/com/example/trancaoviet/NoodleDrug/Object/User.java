package com.example.trancaoviet.NoodleDrug.Object;

import android.graphics.Bitmap;

import java.util.Date;

public class User {
    private String Phone;
    private String Password;
    private String Name;
    private String Email;
    private Date DayOfBirth;
    private Address address;
    private Bitmap Avatar;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Date getDayOfBirth() {
        return DayOfBirth;
    }

    public void setDayOfBirth(Date dayOfBirth) {
        DayOfBirth = dayOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Bitmap getAvatar() {
        return Avatar;
    }

    public void setAvatar(Bitmap avatar) {
        Avatar = avatar;
    }
}
