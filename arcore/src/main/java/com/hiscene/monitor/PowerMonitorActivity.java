package com.hiscene.monitor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hiscene.arcore.R;
import com.hiscene.batteryinfo.BatteryInfoReceiver;
import com.hiscene.utils.PermissionPageUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;

public class PowerMonitorActivity extends AppCompatActivity {
    private static final String TAG = "PowerMonitorTAG";
    private TextView txtCurrent, txtCurrent2;
    private boolean mIsStart = true;

    //要引用的布局文件.
    ConstraintLayout toucherLayout;
    //布局参数.
    WindowManager.LayoutParams params;
    //实例化的WindowManager.
    WindowManager windowManager;

    ImageButton imageButton1;


    //状态栏高度.（接下来会用到）
    int statusBarHeight = -1;

    private BatteryInfoReceiver batteryInfoReceiver;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            checkCurrent();
            if (mIsStart) {
                // 因为Toast.LENGTH_SHORT的默认值是2000
                mHandler.sendEmptyMessageDelayed(0, 6000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_monitor);
        txtCurrent = findViewById(R.id.txt_current);
        mHandler.sendEmptyMessage(0);
        findViewById(R.id.btn_set).setOnClickListener(view -> {
            PermissionPageUtils utils = new PermissionPageUtils(PowerMonitorActivity.this);
            utils.jumpPermissionPage();
//            createToucher();
            checkPermission();
        });

        batteryInfoReceiver = new BatteryInfoReceiver();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(0);
        mIsStart = false;
        //用imageButton检查悬浮窗还在不在，这里可以不要。优化悬浮窗时要用到。
        if (imageButton1 != null) {
            windowManager.removeView(toucherLayout);
        }
    }

    private void checkPermission() {
        //当AndroidSDK>=23及Android版本6.0及以上时，需要获取OVERLAY_PERMISSION.
//使用canDrawOverlays用于检查，下面为其源码。其中也提醒了需要在manifest文件中添加权限.
        /**
         * Checks if the specified context can draw on top of other apps. As of API
         * level 23, an app cannot draw on top of other apps unless it declares the
         * {@link android.Manifest.permission#SYSTEM_ALERT_WINDOW} permission in its
         * manifest, <em>and</em> the user specifically grants the app this
         * capability. To prompt the user to grant this approval, the app must send an
         * intent with the action
         * {@link android.provider.Settings#ACTION_MANAGE_OVERLAY_PERMISSION}, which
         * causes the system to display a permission management screen.
         *
         */
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(PowerMonitorActivity.this)) {
                Log.d(TAG, "checkPermission: canDrawOverlays");
                createToucher();
            } else {
                //若没有权限，提示获取.
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                Toast.makeText(PowerMonitorActivity.this, "需要取得权限以使用悬浮窗", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        } else {
            //SDK在23以下，不用管.
        }
    }

    private void checkCurrent() {
        BatteryManager mBatteryManager = (BatteryManager) getSystemService(Context.BATTERY_SERVICE);
        long avgCurrent = 0, currentNow = 0, powerNow = 0, battery = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            avgCurrent = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE) / 1000;
            currentNow = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW) / 1000;
            powerNow = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER) / 1000;
            battery = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        }
        Log.d(TAG, "BATTERY_PROPERTY_CURRENT_AVERAGE = " + avgCurrent + "mAh");
        Log.d(TAG, "BATTERY_PROPERTY_CURRENT_NOW =  " + currentNow + "mAh");
        Log.d(TAG, "BATTERY_PROPERTY_ENERGY_COUNTER =  " + powerNow + "mWh");
        Log.d(TAG, "checkCurrent: battery " + battery);

        Intent intent = registerReceiver(batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int BatteryV = intent.getIntExtra("voltage", 0);  //电池电压(mv)
        Log.d(TAG, "checkCurrent: BatteryV=" + BatteryV);
//        Log.d(TAG, "checkCurrent: "+getCurrent());
        txtCurrent.setText(currentNow + "mA");
        if (txtCurrent2 != null) {
            txtCurrent2.setText(currentNow + "mA");
        }
    }


    /**
     * 获取当前电流
     */
    private String getCurrent() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "getCurrent: checkSelfPermission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        String result = "null";
        try {
            Class systemProperties = Class.forName("android.os.SystemProperties");
            Method get = systemProperties.getDeclaredMethod("get", String.class);
            String platName = (String) get.invoke(null, "ro.hardware");
            if (platName.startsWith("mt") || platName.startsWith("MT")) {
                String filePath = "/sys/class/power_supply/battery/device/FG_Battery_CurrentConsumption";
                // MTK平台该值不区分充放电，都为负数，要想实现充放电电流增加广播监听充电状态即可
                result = "当前电流为：" + Math.round(getMeanCurrentVal(filePath, 5, 0) / 10.0f) + "mA";
                result += ", 电压为：" + readFile("/sys/class/power_supply/battery/batt_vol", 0) + "mV";
            } else {
                String filePath = "/sys/class/power_supply/battery/current_now";
                int current = Math.round(getMeanCurrentVal(filePath, 5, 5) / 10.0f);
                int voltage = readFile("/sys/class/power_supply/battery/voltage_now", 0) / 1000;
                // 高通平台该值小于0时电池处于放电状态，大于0时处于充电状态
                if (current < 0) {
                    result = "充电电流为：" + (-current) + "mA, 电压为：" + voltage + "mV";
                } else {
                    result = "放电电流为：" + current + "mA, 电压为：" + voltage + "mV";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取平均电流值
     * 获取 filePath 文件 totalCount 次数的平均值，每次采样间隔 intervalMs 时间
     */
    private float getMeanCurrentVal(String filePath, int totalCount, int intervalMs) {
        float meanVal = 0.0f;
        if (totalCount <= 0) {
            return 0.0f;
        }
        for (int i = 0; i < totalCount; i++) {
            try {
                float f = Float.valueOf(readFile(filePath, 0));
                meanVal += f / totalCount;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (intervalMs <= 0) {
                continue;
            }
            try {
                Thread.sleep(intervalMs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return meanVal;
    }

    private int readFile(String path, int defaultValue) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(
                    path));
            int i = Integer.parseInt(bufferedReader.readLine(), 10);
            bufferedReader.close();
            return i;
        } catch (Exception localException) {
            Log.e(TAG, "readFile: error:" + localException);
        }
        return defaultValue;
    }

    private void createToucher() {
        //赋值WindowManager&LayoutParam.
        params = new WindowManager.LayoutParams();
        windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        //设置type.系统提示型窗口，一般都在应用程序窗口之上.
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        //设置效果为背景透明.
        params.format = PixelFormat.RGBA_8888;
        //设置flags.不可聚焦及不可使用按钮对悬浮窗进行操控.
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        //设置窗口初始停靠位置.
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;

        //设置悬浮窗口长宽数据.
        //注意，这里的width和height均使用px而非dp.这里我偷了个懒
        //如果你想完全对应布局设置，需要先获取到机器的dpi
        //px与dp的换算为px = dp * (dpi / 160).
        params.width = 300;
        params.height = 300;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局.
        toucherLayout = (ConstraintLayout) inflater.inflate(R.layout.toucherlayout, null);
        //添加toucherlayout
        windowManager.addView(toucherLayout, params);

        Log.i(TAG, "toucherlayout-->left:" + toucherLayout.getLeft());
        Log.i(TAG, "toucherlayout-->right:" + toucherLayout.getRight());
        Log.i(TAG, "toucherlayout-->top:" + toucherLayout.getTop());
        Log.i(TAG, "toucherlayout-->bottom:" + toucherLayout.getBottom());

        //主动计算出当前View的宽高信息.
        toucherLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        //用于检测状态栏高度.
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        Log.i(TAG, "状态栏高度为:" + statusBarHeight);

        //浮动窗口按钮.
        imageButton1 = (ImageButton) toucherLayout.findViewById(R.id.imageButton1);
        txtCurrent2 = toucherLayout.findViewById(R.id.txt_current);

        //其他代码...
        imageButton1.setOnTouchListener((v, event) -> {
            //ImageButton我放在了布局中心，布局一共300dp
            params.x = (int) event.getRawX() - 150;
            //这就是状态栏偏移量用的地方
            params.y = (int) event.getRawY() - 150 - statusBarHeight;
            windowManager.updateViewLayout(toucherLayout, params);
            return false;
        });
    }


}


