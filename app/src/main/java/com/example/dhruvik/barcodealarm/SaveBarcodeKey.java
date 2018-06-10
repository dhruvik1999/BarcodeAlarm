package com.example.dhruvik.barcodealarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class SaveBarcodeKey {
    Context context;
    SharedPreferences memory1;
    static String BCStr = "BC_CODE";
    public SaveBarcodeKey(Context context){
        this.context = context;
        memory1 = context.getSharedPreferences("com.example.dhruvik.barcodealarm",Context.MODE_PRIVATE);
    }

    public void saveBarcode(String bc){
        if(memory1.getString(BCStr," ").equals(" ")){
            memory1.edit().putString(BCStr,bc).apply();
            Toast.makeText(context,"Barcode | QrCode save",Toast.LENGTH_LONG).show();
        }else{
            replaceBarcode(bc);
        }
    }

    public void replaceBarcode(String bc){
        memory1.edit().clear();
        memory1.edit().putString(BCStr,bc).apply();
        Toast.makeText(context,"Barcode | QrCode Replaced",Toast.LENGTH_LONG).show();
        }

    public String getBarcodeStr(){
        if(!memory1.getString(BCStr," ").equals(" ")){
            return  memory1.getString(BCStr," ");
        }else {
            Toast.makeText(context,"Barcode is not applied",Toast.LENGTH_LONG).show();
            return "-1";
        }
    }
}
