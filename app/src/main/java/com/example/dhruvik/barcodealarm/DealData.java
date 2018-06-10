package com.example.dhruvik.barcodealarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class DealData {

    static ArrayList<Integer> timeList = new ArrayList<Integer>();
    static ArrayList<Integer> permiss1 = new ArrayList<Integer>();
    static ArrayList<Integer> permiss2 = new ArrayList<Integer>();
    static ArrayList<Integer> listRequestId  = new ArrayList<Integer>();

    Context context;
    public DealData(Context context){
        this.context = context;
    }


    public void getData(){

        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String projection[] = new String[]{"sno",DatabaseHelper.col1,DatabaseHelper.col2,DatabaseHelper.col3};

        Cursor c = db.query(DatabaseHelper.TABLE_NAME,projection,null,null,null,null,null,null);


        c.moveToFirst();


        timeList.clear();
        permiss1.clear();
        permiss2.clear();
        listRequestId.clear();

        for(int i=0;i<c.getCount();i++) {
            Log.i("DATA", c.getInt(c.getColumnIndex("sno"))+" "+c.getInt(c.getColumnIndex(DatabaseHelper.col1)) + " " + c.getInt(c.getColumnIndex(DatabaseHelper.col2)) + " " + c.getInt(c.getColumnIndex(DatabaseHelper.col3)));


            timeList.add(c.getInt(c.getColumnIndex(DatabaseHelper.col1)));
            permiss1.add(c.getInt(c.getColumnIndex(DatabaseHelper.col2)));
            permiss2.add(c.getInt(c.getColumnIndex(DatabaseHelper.col3)));
            listRequestId.add(c.getInt(c.getColumnIndex("sno")));

                c.moveToNext();
        }

        db.close();
sortData();
    }


    public void setData(int time,int abc ,int aact){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.col1,time);
        contentValues.put(DatabaseHelper.col2,abc);
        contentValues.put(DatabaseHelper.col3,aact);

        db.insert(DatabaseHelper.TABLE_NAME,null,contentValues);


        getData();
        db.close();
    }

    private void sortData(){

        int temp1,temp2,temp3,temp0;
        Calendar calendar = Calendar.getInstance();

        for(int i=0;i<timeList.size();i++){
            for(int j=0;j<timeList.size();j++){
                int p1 = (timeList.get(i)/100)*60 + timeList.get(i)%100 - calendar.get(Calendar.HOUR_OF_DAY)*60 - calendar.get(Calendar.MINUTE);
                if(p1 < 0)
                    p1 += 24*60;

                int p2 = (timeList.get(j)/100)*60 + timeList.get(j)%100 - calendar.get(Calendar.HOUR_OF_DAY)*60 - calendar.get(Calendar.MINUTE);
                if(p2 < 0)
                    p2 += 24*60;

                if(p1 < p2){
                    temp0 = listRequestId.get(i);
                    temp1 = timeList.get(i);
                    temp2 = permiss1.get(i);
                    temp3 = permiss2.get(i);

                    listRequestId.set(i,listRequestId.get(j));
                    timeList.set(i,timeList.get(j));
                    permiss1.set(i,permiss1.get(j));
                    permiss2.set(i,permiss2.get(j));

                    listRequestId.set(j,temp0);
                    timeList.set(j,temp1);
                    permiss1.set(j,temp2);
                    permiss2.set(j,temp3);
                }
            }
        }
    }

     public int getId(){
        getData();
        return timeList.size();
    }

    public int getRequestCode(int TTime){
        int result = -1;
        getData();

        for(int i=0;i<listRequestId.size();i++){
            if(TTime == timeList.get(i)){
                result = listRequestId.get(i);
                break;
            }
        }
        return result;
    }

    public void setDataAtPosition(int id , int time , int perm1 , int perm2){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        helper.onUpgrade(db,1,1);
        ContentValues cv = new ContentValues();
        for(int i=0; i<listRequestId.size() ; i++){

            if(listRequestId.get(i) == id){

                timeList.set(i , time);
                permiss1.set(i,perm1);
                permiss2.set(i,perm2);
            }

            cv.put(DatabaseHelper.col0 , listRequestId.get(i));
            cv.put(DatabaseHelper.col1 , timeList.get(i));
            cv.put(DatabaseHelper.col2 , permiss1.get(i));
            cv.put(DatabaseHelper.col3 , permiss2.get(i));

            db.insert(DatabaseHelper.TABLE_NAME,null,cv);
        }


    }
    static int convertTime(String strTime){
        int intTime = 941;
        int hour = 0,minute = 0;
        char tar = ' ';
        String s1 = "",s2 = "";
        boolean t = true;

        for(int i=0;i<strTime.length();i++){
            if(tar == strTime.charAt(i)){
                t  = false;
                i = i + 2;
            }else{
                if(t){
                  s1 = s1 + strTime.charAt(i);
                }else {
                    s2 = s2 + strTime.charAt(i);
                }
            }
        }

     intTime = Integer.parseInt(s1) * 100 + Integer.parseInt(s2);
        Log.i("t|",s1 + "|" + s2);

        return intTime;
    }

}
