package com.example.dhruvik.barcodealarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.Toast;

public class HHome extends AppCompatActivity {

    GridView gridView;
    DealData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hhome);

    initiallization();
        gridView.setAdapter(new GridAdapter(this));

    }

    private void initiallization() {
        data = new DealData(this);
        gridView = (GridView)findViewById(R.id.grid);
    }

    public void addAlarm(View view) {
        startActivity(new Intent(getApplicationContext(), AddAlarm.class));
        finish();
    }

    public void setg(View view) {
        startActivity(new Intent(getApplicationContext(), Settings.class));
    }
}
