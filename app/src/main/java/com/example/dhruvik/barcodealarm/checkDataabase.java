package com.example.dhruvik.barcodealarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class checkDataabase {
    Context context;
    public checkDataabase(Context context){
        this.context = context;
    }

    public void getData(){

        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String projection[] = new String[]{DatabaseHelper.col1,DatabaseHelper.col2,DatabaseHelper.col3};

        Cursor c = db.query(DatabaseHelper.TABLE_NAME,projection,null,null,null,null,null,null);

        if(c== null){
            c.moveToFirst();
        }
c.moveToFirst();

        for(int i=0;i<c.getCount();i++) {
            Log.i("DATA", c.getInt(c.getColumnIndex(DatabaseHelper.col1)) + " " + c.getInt(c.getColumnIndex(DatabaseHelper.col2)) + " " + c.getInt(c.getColumnIndex(DatabaseHelper.col3)));

            if(i != c.getColumnCount()-1)
            c.moveToNext();
        }
    }

    public void setData(){


        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.col1,1125);
        contentValues.put(DatabaseHelper.col2,1);
        contentValues.put(DatabaseHelper.col3,2);

        db.insert(DatabaseHelper.TABLE_NAME,null,contentValues);

    }

}
