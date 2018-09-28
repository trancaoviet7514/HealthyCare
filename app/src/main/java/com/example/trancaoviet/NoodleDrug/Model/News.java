package com.example.trancaoviet.NoodleDrug.Model;

import java.io.Serializable;

public class News implements Serializable {
    private String Title;
    private String ImageUrl;
    private String ContentUrl;

    public News() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getContentUrl() {
        return ContentUrl;
    }

    public void setContentUrl(String contentUrl) {
        ContentUrl = contentUrl;
    }
}
