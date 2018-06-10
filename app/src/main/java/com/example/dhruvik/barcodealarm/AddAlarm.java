package com.example.dhruvik.barcodealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddAlarm extends AppCompatActivity {


DealData data  = new DealData(this);
    TimePicker  tp;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    int counter = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        tp =  (TimePicker)this.findViewById(R.id.tp);
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
    }

    public void cancelAlarm(View view) {
        CancelAlarm cancelAlarm = new CancelAlarm(this);
        cancelAlarm.cancelAction();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setAlarm(View view) {

            Toast.makeText(getApplicationContext(), "set", Toast.LENGTH_SHORT).show();

            Date date = new Date();

            Calendar cal_now = Calendar.getInstance();
            Calendar cal_alarm = Calendar.getInstance();


            cal_now.setTime(date);
            cal_alarm.setTime(date);

            cal_alarm.set(Calendar.HOUR_OF_DAY, tp.getHour());
            cal_alarm.set(Calendar.MINUTE, tp.getMinute());
            cal_alarm.set(Calendar.SECOND, 0);

            if (cal_alarm.before(cal_now)) {
                cal_alarm.add(Calendar.DATE, 1);
            }

            data.setData(tp.getHour()*100 + tp.getMinute(),0,1);

            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), data.getId(), intent, 0);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
            counter++;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),HHome.class));
        finish();
    }
}
