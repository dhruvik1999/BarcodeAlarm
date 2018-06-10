package com.example.dhruvik.barcodealarm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class GridAdapter extends BaseAdapter {
    DealData data;
    Context context;
    Calendar calendar;
    SaveBarcodeKey saveBarcodeKey;

    int currentTime;
    int currentId,currentPerm1,currentPerm2;

    TextView ttime,disc;
    Switch perm1,perm2;

    public GridAdapter(Context context){
        data = new DealData(context);
        this.context = context;
        data.getData();
        saveBarcodeKey = new SaveBarcodeKey(context);
    }

    @Override
    public int getCount() {
        return DealData.timeList.size();
    }

    @Override
    public Object getItem(int position) {
        return DealData.timeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        calendar = Calendar.getInstance();


        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custum,null);
        }
        ttime = (TextView)view.findViewById(R.id.Ttime);
        disc = (TextView)view.findViewById(R.id.disc);
        perm1 = (Switch)view.findViewById(R.id.Ebarcode);
        perm2 = (Switch)view.findViewById(R.id.activate);

        ttime.setText(String.valueOf(DealData.timeList.get(position)/100) + " : " + String.valueOf(DealData.timeList.get(position)%100));

        int im = (DealData.timeList.get(position)/100)*60 + DealData.timeList.get(position)%100 - calendar.get(Calendar.HOUR_OF_DAY)*60 - calendar.get(Calendar.MINUTE);

        if(im <0){
                im = im + 24*60;
        }

        String s = "Remainning " + String.valueOf(im/60) + " Hours and " + String.valueOf(im%60) + " Minutes ";
        disc.setText(s);

        if(DealData.permiss1.get(position) == 1){
            perm1.setChecked(true);
        }else {
            perm1.setChecked(false);
        }
        if(DealData.permiss2.get(position) == 1){
            perm2.setChecked(true);
        }else {
            perm2.setChecked(false);
        }

        ttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCurrentSituation();
                int tar = DealData.listRequestId.get(position);
                EditAlarm.id = tar;
                EditAlarm.perm1 = currentPerm1;
                EditAlarm.perm2 = currentPerm2;
                context.startActivity(new Intent(context,EditAlarm.class));
            }
        });

        perm1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int tar = DealData.listRequestId.get(position);
                getCurrentSituation();
                if(isChecked){
                    if(!saveBarcodeKey.getBarcodeStr().equals("-1")){
                        Toast.makeText(context,"Barcode is Enable",Toast.LENGTH_SHORT).show();
                        new DealData(context).setDataAtPosition(tar,currentTime,1,currentPerm2);
                        //save permission in db
                    }else {
                        context.startActivity(new Intent(context,Settings.class));
                    }
                }else{
                    //code for disable barcode
                    new DealData(context).setDataAtPosition(tar,currentTime,0,currentPerm2);
                    Toast.makeText(context,"Barcode is Disable",Toast.LENGTH_SHORT).show();
                }
            }
        });

        perm2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                getCurrentSituation();
                int tar = DealData.listRequestId.get(position);

                if(isChecked){
                    new DealData(context).setDataAtPosition(tar,currentTime,currentPerm1,1);
                    new CancelAlarm(context).setAlaramAtId(tar,currentTime);
                    Toast.makeText(context,"alarm is activate",Toast.LENGTH_SHORT).show();
                }else{
                    new CancelAlarm(context).cancelAlaraamAtId(tar);
                    new DealData(context).setDataAtPosition(tar,currentTime,currentPerm1,0);
                    Toast.makeText(context,"alarm is deactivate",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void getCurrentSituation(){
        currentTime = DealData.convertTime(ttime.getText().toString());

        if(perm1.isChecked()){
            currentPerm1 = 1;
        }else{
            currentPerm1 = 0;
        }

        if(perm2.isChecked()){
            currentPerm2 = 1;
        }else{
            currentPerm2 = 0;
        }

    }
}
