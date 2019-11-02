package com.hiscene.demo3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hiscene.demo3.eventbus.EventBus;
import com.hiscene.demo3.eventbus.Subscribe;
import com.hiscene.demo3.eventbus.ThreadMode;
import com.hiscene.demo3.eventbus.UserInfo;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivityTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //告诉EventBus将本类的方法都放到eventbus中管理
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        findViewById(R.id.btn_goto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void aa(UserInfo userInfo){
        Log.d(TAG, "aa: "+userInfo.toString());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void bb(String name){

    }
}
