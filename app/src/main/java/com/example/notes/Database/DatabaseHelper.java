package com.example.notes.Database;

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
    private final String table_name_1 = "Notes";
    private final String t1_col_1 = "ID";
    private final String t1_col_2 = "NOTE";
    private final String t1_col_3 = "updatedAt";

    private final String table_name_2 = "TASKS";
    private final String t2_col_1 = "ID";
    private final String t2_col_2 = "TASK";
    private final String t2_col_4 = "ACTIVE";
    private final String t2_col_3 = "updatedAt";


    public DatabaseHelper(@Nullable Context context) {
        super(context, db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name_1 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOTE TEXT, UPDATEDAT TEXT)");
        db.execSQL("create table " + table_name_2 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT, UPDATEDAT TEXT, ACTIVE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table_name_1);
        db.execSQL("drop table if exists " + table_name_2);
        onCreate(db);
    }

    public boolean insert_table1(String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        String time = getCurrentDateAndTime();
        ContentValues values = new ContentValues();
        values.put(this.t1_col_2, note);
        values.put(this.t1_col_3, time);
        return db.insert(table_name_1, null, values) == -1;
    }

    public boolean insert_table2(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        String time = getCurrentDateAndTime();
        ContentValues values = new ContentValues();
        values.put(this.t2_col_2, task);
        values.put(this.t2_col_3, time);
        values.put(this.t2_col_4, 1);
        return db.insert(table_name_2, null, values) == -1;
    }

    public Cursor getAllData_table1() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + table_name_1, null);
        return res;
    }

    public Cursor getAllData_table2() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + table_name_2 + " ORDER BY ID DESC, ACTIVE DESC", null);
        return res;
    }

    public boolean update_table1(String id, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String time = getCurrentDateAndTime();
        values.put(this.t1_col_2, note);
        values.put(this.t1_col_3, time);
        db.update(table_name_1, values, "ID = ?", new String[] { id });
        return true;
    }

    public boolean update_table2(String id, String task, int active) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String time = getCurrentDateAndTime();
        values.put(this.t2_col_2, task);
        values.put(this.t2_col_3, time);
        values.put(this.t2_col_4, active);
        db.update(table_name_2, values, "ID = ?", new String[] { id });
        return true;
    }

    public boolean delete_table1(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name_1, "ID = ?", new String[] { id });
        return true;
    }

    public boolean delete_table2(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name_2, "ID = ?", new String[] { id });
        return true;
    }

    private String getCurrentDateAndTime() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(format1);
    }
}