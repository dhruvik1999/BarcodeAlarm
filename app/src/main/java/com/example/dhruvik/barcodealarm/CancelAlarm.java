package com.example.dhruvik.barcodealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class CancelAlarm {
    static String idKey ="ID";
    Context context;
    SharedPreferences memory;
    AlarmManager am;
    public CancelAlarm(Context context){
        this.context = context;
        memory = context.getSharedPreferences("com.example.dhruvik.barcodealarm",Context.MODE_PRIVATE);
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

    }

    public void cancelAlarm(){
        try{
            Calendar cal = Calendar.getInstance();
            int tar = cal.get(Calendar.HOUR_OF_DAY)*100 + cal.get(Calendar.MINUTE);
            DealData dealData = new DealData(context);

            int rc = dealData.getRequestCode(tar);
            //Intent intent = new Intent(context,AlarmReceiver.class);
            //PendingIntent pendingIntent = PendingIntent.getBroadcast(context,rc,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            //am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            //am.cancel(pendingIntent);

            Log.i("|||||||||||||||||||||",rc + "");
            saveID(rc);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void saveID(int id){
        if(memory.getInt(idKey,-1) != -1){
            cancelAction();
            memory.edit().putInt(idKey,id).apply();
        }else{
            //cancelAction();
            memory.edit().putInt(idKey,id).apply();
        }
    }

    public void cancelAction(){
        if(memory.getInt(idKey,-1)!=-1) {

            Toast.makeText(context,"alarm cancel",Toast.LENGTH_SHORT).show();
            try {
                Scanner.boundary = false;
                SimpleOffScreen.boundary = false;
                AlarmReceiver.ring.stop();

            }catch (Exception e){
                e.printStackTrace();
            }

            Intent intent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, memory.getInt(idKey, -1), intent,0);
            am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            am.cancel(pendingIntent);
            memory.edit().putInt(idKey,-1).apply();

        }else {
            Toast.makeText(context,"No alarm is ringing....",Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelAlaraamAtId(int id ){
        AlarmManager am  = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);

        am.cancel(pendingIntent);

    }

    public void setAlaramAtId(int id , int time){


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Date date = new Date();

        Calendar cal_now = Calendar.getInstance();
        Calendar cal_alarm = Calendar.getInstance();


        cal_now.setTime(date);
        cal_alarm.setTime(date);

        cal_alarm.set(Calendar.HOUR_OF_DAY, time/100);
        cal_alarm.set(Calendar.MINUTE, time%100);
        cal_alarm.set(Calendar.SECOND, 0);

        if (cal_alarm.before(cal_now)) {
            cal_alarm.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);

    }
}
