package com.example.a123.recepts_project_university.database;

import android.database.Cursor;
import android.database.CursorWrapper;

public class ReceptsCursorWrapper extends CursorWrapper {
    public ReceptsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

//    public Recept getRecept(){
//        String id = getString(getColumnIndex(ReceptsDbSchema.ReceptsTable.Cols.UUID));
//        String
//        return
//    }
}
