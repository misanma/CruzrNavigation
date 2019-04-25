package com.example.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "Databasehelper";

    private static final String TABLE_NAME = "Locations";
    private static final String Col1 = "ID";
    private static final String Col2 = "Name";
    private static final String Col3 = "Description";
    private static final String Col4 = "X-cord";
    private static final String Col5 = "Y-cord";
    private static final String Col6 = "Z-cord";

    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null ,1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Col2 + " TEXT, "
                + Col3 + " TEXT, "
                + Col4 + " INTEGER, "
                + Col5 + " INTEGER, "
                + Col6 + " INTEGER) ";
        db.execSQL(createTable);
    }
    
}
