package com.example.a123.recepts_project_university;

import android.app.Application;

import com.example.a123.recepts_project_university.model.AppDbHelper;
import com.example.a123.recepts_project_university.model.DbOpenHelper;
import com.example.a123.recepts_project_university.model.TakeDb;


public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DbOpenHelper dbOpenHelper = new DbOpenHelper(this, "receipt-db");
        AppDbHelper appDbHelper = new AppDbHelper(dbOpenHelper);
        TakeDb.getInstance(dbOpenHelper);
    }
}
