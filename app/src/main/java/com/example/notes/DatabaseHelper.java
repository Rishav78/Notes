package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String db_name = "notes.db";
    private final String table_name = "id";
    private final String col_1 = "ID";
    private final String col_2 = "NOTE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOTE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table_name);
        onCreate(db);
    }

    public boolean insert(String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(this.col_2, note);
        return db.insert(table_name, null, content) == -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + table_name, null);
        return res;
    }

    public boolean update(String id, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.col_2, note);
        db.update(table_name, values, "ID = ?", new String[] { id });
        return true;
    }
}