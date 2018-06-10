package com.example.dhruvik.barcodealarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    Button addBarcode;
    TextView bcNo;
    SaveBarcodeKey saveBarcodeKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        addBarcode = (Button)this.findViewById(R.id.addBC);
        bcNo = (TextView)this.findViewById(R.id.bcNo);
        saveBarcodeKey = new SaveBarcodeKey(this);

        bcNo.setText(saveBarcodeKey.getBarcodeStr());

        addBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AcceptBarcode.class));
                finish();
            }
        });
    }
    public void help(View view) {
        startActivity(new Intent(getApplicationContext(),Help.class));
    }
    public void about(View view){
        startActivity(new Intent(getApplicationContext(),about.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}