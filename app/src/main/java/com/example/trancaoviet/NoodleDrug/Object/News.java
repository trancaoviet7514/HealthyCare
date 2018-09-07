package com.example.trancaoviet.NoodleDrug.Object;

import android.graphics.Bitmap;

public class News {
    private String Title;
    private Bitmap Image;

    public News(String title, Bitmap image) {
        Title = title;
        Image = image;
    }

    public News() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Bitmap getImage() {
        return Image;
    }

    public void setImage(Bitmap image) {
        Image = image;
    }
}
