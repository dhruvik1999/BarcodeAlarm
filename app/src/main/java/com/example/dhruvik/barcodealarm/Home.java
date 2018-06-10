package com.example.dhruvik.barcodealarm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Date;

public class Home extends AppCompatActivity {

    GridView gridView;
    static Context context;
    DealData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = getApplicationContext();

        initiallization();

    }

    private void initiallization() {
        data = new DealData(this);
        gridView = (GridView)findViewById(R.id.grid);
    }

    public void addAlarm(View view) {
        startActivity(new Intent(context, AddAlarm.class));
    }

    public void setg(View view) {
        startActivity(new Intent(context, Settings.class));
    }
}