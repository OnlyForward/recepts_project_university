package com.example.a123.recepts_project_university.model;

public class TakeDb {
    private static TakeDb ourInstance;
    private static AppDbHelper appDbHelper;

    public static TakeDb getInstance(DbOpenHelper dbOpenHelper) {
        if(ourInstance==null){
            ourInstance = new TakeDb(dbOpenHelper);
        }
        return ourInstance;
    }

    public static AppDbHelper getAppDbHelper(){
        return appDbHelper;
    }

    private TakeDb(DbOpenHelper dbOpenHelper) {
        appDbHelper = new AppDbHelper(dbOpenHelper);
    }
}
