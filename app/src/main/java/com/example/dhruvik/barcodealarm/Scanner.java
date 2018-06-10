package com.example.dhruvik.barcodealarm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private  ZXingScannerView zXingScannerView;
    LayoutInflater mInflater;

    SaveBarcodeKey bcKey;
    CancelAlarm cancelAlarm;
    long timeLimite = 20 * 60 * 1000;
    long timeInterval = 1000;
    static  boolean boundary = true;
    private PowerManager.WakeLock wl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
                requestPermission();



        bcKey = new SaveBarcodeKey(this);
        cancelAlarm = new CancelAlarm(this);

        new CountDownTimer(timeLimite,timeInterval) {
            @Override
            public void onTick(long millisUntilFinished) {

                if(boundary) {

                    if (AlarmReceiver.ring.isPlaying()) {
                        // nothing to do at here
                    } else {
                        Toast.makeText(getApplicationContext(), "ringing....", Toast.LENGTH_SHORT).show();
                        AlarmReceiver.ring.play();
                    }
                }
            }

            @Override
            public void onFinish() {
                    Toast.makeText(getApplicationContext(),"Alarm ring stop",Toast.LENGTH_SHORT).show();
            }
        }.start();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            zXingScannerView = new ZXingScannerView(this);
            setContentView(zXingScannerView);
            zXingScannerView.setResultHandler(this);
            zXingScannerView.startCamera();
        }
        else{
            requestPermission();
        }
    }

    @Override
    public void handleResult(Result result) {
        Toast.makeText(this,result.getText(),Toast.LENGTH_SHORT).show();
        zXingScannerView.resumeCameraPreview(this);

        if(result.getText().equals(bcKey.getBarcodeStr())){
            cancelAlarm.cancelAction();
            Toast.makeText(getApplicationContext(),"Barcode | Qrcode is matched",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),HHome.class));
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"not matched!! \n try again....",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                zXingScannerView = new ZXingScannerView(this);
                setContentView(zXingScannerView);
                zXingScannerView.setResultHandler(this);
                zXingScannerView.startCamera();
            }
        }else{
            Toast.makeText(this,"permission is refused",Toast.LENGTH_SHORT).show();
        }
    }
    private void clearFlags(){

    }
}
