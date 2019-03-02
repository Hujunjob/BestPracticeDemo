package com.hiscene.batteryinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BatteryInfoReceiver extends BroadcastReceiver {
    private static final String TAG = "BatteryInfoReceiverTAG";
    Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {

        mContext=context;
        String action = intent.getAction();

        //如果捕捉到的action是ACTION_BATTERY_CHANGED， 就运行onBatteryInfoReceiver()

        if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
            int BatteryN = intent.getIntExtra("level", 0);    //目前电量（0~100）
            int BatteryV = intent.getIntExtra("voltage", 0);  //电池电压(mv)
            int BatteryT = intent.getIntExtra("temperature", 0);  //电池温度(数值)
            String BatteryStyle=intent.getStringExtra("technology"); //电池类型
            int BatteryScale=intent.getIntExtra("scale",0);//电池容量
//            String BatteryMcc=intent.getStringExtra("max_charging_current");//最大充电电流
//            String BatteryMcv=intent.getStringExtra("max_charging_voltage");//最大充电电压
//            String BatteryCc=intent.getStringExtra("charge_counter");//累计充电次数
//            String BatterySeq=intent.getStringExtra("seq");//当前更新序列号
            int BatteryNow=intent.getIntExtra("BATTERY_PROPERTY_CURRENT_NOW",0);//瞬时电流
            int BatteryAverage=intent.getIntExtra("BATTERY_PROPERTY_CURRENT_AVERAGE",0);//平均电流

            Log.d(TAG, "onReceive: voltage="+BatteryV+",BatteryNow="+BatteryNow+",BatteryAverage="+BatteryAverage);
        }
    }

}
