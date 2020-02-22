package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String db_name = "notes.db";
    private final String table_name = "id";
    private final String col_1 = "ID";
    private final String col_2 = "NOTE";
    private final String col_3 = "updatedAt";

    public DatabaseHelper(@Nullable Context context) {
        super(context, db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOTE TEXT, UPDATEDAT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table_name);
        onCreate(db);
    }

    public boolean insert(String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        String time = getCurrentDateAndTime();
        ContentValues values = new ContentValues();
        values.put(this.col_2, note);
        values.put(this.col_3, time);
        return db.insert(table_name, null, values) == -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + table_name, null);
        return res;
    }

    public boolean update(String id, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String time = getCurrentDateAndTime();
        values.put(this.col_2, note);
        values.put(this.col_3, time);
        db.update(table_name, values, "ID = ?", new String[] { id });
        return true;
    }

    public boolean delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name, "ID = ?", new String[] { id });
        return true;
    }

    private String getCurrentDateAndTime() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(format1);
    }
}