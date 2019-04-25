package com.example.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = ".Databasehelper";

    private static final String TABLE_NAME = "Locations";
    private static final String Col1 = "ID";
    private static final String Col2 = "Name";
    private static final String Col3 = "Description";
    private static final String Col4 = "Xcord";
    private static final String Col5 = "Ycord";
    private static final String Col6 = "Zcord";

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

    public boolean addData(String name, String Desciption, int x, int y, int z){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col2, name);
        contentValues.put(Col3, Desciption);
        contentValues.put(Col4, x);
        contentValues.put(Col5, y);
        contentValues.put(Col6, z);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result == -1;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    public boolean deleteDataByName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "DELETE FROM " + TABLE_NAME + "WHERE Col1 is" + name;
        //long result = db.delete(TABLE_NAME, Col2 + " = \"" + name + "\"", null);
        long result = db.delete(TABLE_NAME, Col2 + " = \"" + name + "\"", null);
        return true;
    }
}
