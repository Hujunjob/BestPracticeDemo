package com.hiscene.batteryinfo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;

import com.hiscene.arcore.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static int BatteryN;       //目前电量
    public static int BatteryV;       //电池电压
    public static double BatteryT;        //电池温度
    public static String BatteryStatus;   //电池状态
    public static String BatteryTemp;     //电池使用情况
    public static String BatteryStyle;  //电池类型
    public static String BatteryPlugged;  //充电方式
    public static String BatteryPresent;//是否存在电池
    public static int BatteryScale;//电池容量
    public static String BatteryMcc;//最大充电电流
    public static String BatteryMcv;//最大充电电压
    public static String BatteryCc;//累计充电次数
    public static String BatterySeq;//累计充电次数
    public static int BatteryNow;//瞬时电流
    public static int BatteryAverage;//平均电流

    public static ListView lv;
    public BatteryInfoReceiver batteryInfoReceiver;

    public static List<Data> dataList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        batteryInfoReceiver=new BatteryInfoReceiver();
        registerReceiver(batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(batteryInfoReceiver);
        super.onDestroy();
    }
}
