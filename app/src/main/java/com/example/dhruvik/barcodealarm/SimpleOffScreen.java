package com.example.dhruvik.barcodealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class SimpleOffScreen extends AppCompatActivity {


    Button stop,snooz;
    TextView  showTime;
    Calendar calNow,calAlarm;

    long timeLmit = 10 * 60 * 1000;
    long timeInterval = 1000;
    int tar;
    int RequestCode;
static boolean boundary = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_off_screen);
        initiallixation();

Date date = new Date();
calNow.setTime(date);
calAlarm.setTime(date);

tar = calNow.get(Calendar.HOUR_OF_DAY) * 100 + calNow.get(Calendar.MINUTE);

        new CountDownTimer(timeLmit,timeInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(boundary) {

                    if (AlarmReceiver.ring.isPlaying()) {
                        //nothing to do
                    } else {
                        AlarmReceiver.ring.play();
                    }
                }
            }
            @Override
            public void onFinish() {

            }
        }.start();

        showTime.setText(calNow.get(Calendar.HOUR_OF_DAY) + " : " + calNow.get(Calendar.MINUTE));
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CancelAlarm cancelAlarm = new CancelAlarm(getApplicationContext());
                cancelAlarm.cancelAction();
            }
        });

        snooz.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"UTHO JAGO AND DHYEY PRAPTI SUDHI MANDYA RAHO",Toast.LENGTH_SHORT).show();
                //code for snoozinfg the alarm..

                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                calAlarm.set(Calendar.HOUR_OF_DAY,calNow.get(Calendar.HOUR));
                calAlarm.set(Calendar.MINUTE,calNow.get(Calendar.MINUTE));
                calAlarm.set(Calendar.SECOND,0);

                if (calAlarm.before(calNow)) {
                    calAlarm.add(Calendar.DATE, 1);
                }

                Intent io = new Intent(getApplicationContext(),AlarmReceiver.class);
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(),new DealData(getApplicationContext()).getRequestCode(tar),io,0);

                am.setRepeating(AlarmManager.RTC_WAKEUP,calAlarm.getTimeInMillis(),10 * 60 * 1000,pi );

            }
        });
    }

    private void initiallixation(){
        stop = (Button)this.findViewById(R.id.stop);
        snooz = (Button)this.findViewById(R.id.snooz);
        showTime = (TextView)this.findViewById(R.id.showTime);
        calNow = Calendar.getInstance();
        calAlarm = Calendar.getInstance();
    }
}
