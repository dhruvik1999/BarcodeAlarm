package com.example.dhruvik.barcodealarm;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FirestHome extends AppCompatActivity {

    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firest_home);

        context = getApplicationContext();

        WaitTask waitTask = new WaitTask();
        waitTask.execute();

    }

    public class WaitTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Thread.sleep(4000);
            }catch(Exception e){
                e.printStackTrace();
            }

            //download task of all the data from shared prefrences

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(new Intent(context,HHome.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
