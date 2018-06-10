package com.example.dhruvik.barcodealarm;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    Context context;
    Calendar calendar;
    int sno,permiss = 0;
    DealData data;
    static Ringtone ring;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        defaultAction();
        calendar = Calendar.getInstance();
        data = new DealData(context);

        Toast.makeText(context,"Wake Up......",Toast.LENGTH_SHORT).show();
     /*
        String msg = intent.getStringExtra("message");

        Intent myIntent = new Intent(context,Scanner.class);
        Bundle bundle = new Bundle();
        bundle.putString("message",msg);
        myIntent.putExtras(bundle);
        myIntent.setFlags(Intent.FLAG_FROM_BACKGROUND);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        context.startActivity(myIntent);

        */

     try{
         Uri  notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
         Intent i  = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
         context.startActivity(i);
         ring = RingtoneManager.getRingtone(context,notification);
     }catch (Exception e){
         e.printStackTrace();
     }


        int tar = calendar.get(Calendar.HOUR_OF_DAY) * 100 + calendar.get(Calendar.MINUTE);
        sno = data.getRequestCode(tar);



        for(int i=0;i<DealData.permiss1.size();i++) {
            if(DealData.listRequestId.get(i) == sno) {
                if (DealData.permiss1.get(i) == 1) {
                    ring.play();
                    Intent io = new Intent();
                    io.setClassName("com.example.dhruvik.barcodealarm","com.example.dhruvik.barcodealarm.Scanner");
                    io.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(io);
                } else {
                    ring.play();
                    Intent io = new Intent();
                    io.setClassName("com.example.dhruvik.barcodealarm","com.example.dhruvik.barcodealarm.SimpleOffScreen");
                    io.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(io);
                }
            }else{

            }
        }


    }

    private void defaultAction(){
        CancelAlarm cancelAlarm = new CancelAlarm(context);
        cancelAlarm.cancelAlarm();
    }
}
