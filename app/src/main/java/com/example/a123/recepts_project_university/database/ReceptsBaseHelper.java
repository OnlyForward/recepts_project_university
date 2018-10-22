package com.example.a123.recepts_project_university.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReceptsBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    public static final String DATABASE_NAME = "receptsBase.db";

    public ReceptsBaseHelper(Context context) {
        super(context, DATABASE_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ReceptsDbSchema.ReceptsTable.Name+ "(" + " _id integer primary key autoincrement, "
                + ReceptsDbSchema.ReceptsTable.Cols.UUID
                +", "+ReceptsDbSchema.ReceptsTable.Cols.TITLE
                +", "+ReceptsDbSchema.ReceptsTable.Cols.DESCRIPTION+
                ", "+ReceptsDbSchema.ReceptsTable.Cols.SAVED+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
