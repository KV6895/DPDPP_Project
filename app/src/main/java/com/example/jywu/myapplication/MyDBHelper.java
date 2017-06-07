package com.example.jywu.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kevin on 2017/5/15.
 */

public class MyDBHelper extends SQLiteOpenHelper {
    final public  String TABLE_NAME = "count";
    public  MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE  TABLE " +TABLE_NAME+
                "(_id INTEGER PRIMARY KEY  NOT NULL , " +
                "book TEXT NOT NULL , " +
                "cdate DATETIME NOT NULL, "+
                "info TEXT NOT NULL, " +
                "remarks TEXT NOT NULL," +
                "amount  INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
