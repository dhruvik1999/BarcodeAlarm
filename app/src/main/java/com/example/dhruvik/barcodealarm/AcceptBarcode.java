package com.example.dhruvik.barcodealarm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class AcceptBarcode extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private  ZXingScannerView zXingScannerView;
    SaveBarcodeKey save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_barcode);

        save = new SaveBarcodeKey(this);
        requestPermission();
    }

    @Override
    public void handleResult(Result result) {
        Toast.makeText(this,result.getText(),Toast.LENGTH_SHORT).show();
        save.saveBarcode(result.getText());



        finish();
    }

    public void BCscanner(View view){
        Toast.makeText(getApplicationContext(),"Buton is pressed",Toast.LENGTH_SHORT).show();

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
    protected void onPause() {
        super.onPause();
//        zXingScannerView.stopCamera();

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
}
