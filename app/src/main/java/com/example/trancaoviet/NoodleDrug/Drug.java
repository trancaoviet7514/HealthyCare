package com.example.trancaoviet.NoodleDrug;

import android.graphics.Bitmap;

public class Drug {

    private int id;
    private String name;
    private String component;
    private String useCase;
    private Bitmap image;

    public Drug(int id, String name, String component, String useCase, Bitmap image) {
        this.id = id;
        this.name = name;
        this.component = component;
        this.useCase = useCase;
        this.image = image;
    }

    public Drug() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getUseCase() {
        return useCase;
    }

    public void setUseCase(String useCase) {
        this.useCase = useCase;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
