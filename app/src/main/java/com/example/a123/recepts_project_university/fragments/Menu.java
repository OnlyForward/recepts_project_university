package com.example.a123.recepts_project_university.fragments;

import android.view.View;

import java.util.UUID;

public class Menu {
    private UUID mUUID;
    private String mName;
    private String mImage;
    private int number;
    private int visibleornot = View.INVISIBLE;

    public int getVisibleornot() {
        return visibleornot;
    }

    public void setVisibleornot(int visibleornot) {
        this.visibleornot = visibleornot;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public Menu(){
        mUUID = UUID.randomUUID();
    }

    public Menu(UUID id){
        mUUID = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }
}
