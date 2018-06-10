package com.example.dhruvik.barcodealarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static String TABLE_NAME = "AlarmData";
    static String col0 = "sno";
    static String col1 = "Ttime";
    static String col2 = "ABC";
    static String col3 = "AAct";

    public DatabaseHelper(Context context) {
        super(context, "dhruvik1999", null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(sno INTEGER PRIMARY KEY AUTOINCREMENT,Ttime INTEGER,ABC INTEGER,AAct INTEGER);" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(db);
    }
}
