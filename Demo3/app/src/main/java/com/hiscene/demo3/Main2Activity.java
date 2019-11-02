package com.hiscene.demo3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hiscene.demo3.eventbus.EventBus;
import com.hiscene.demo3.eventbus.UserInfo;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        EventBus.getDefault().post(new UserInfo("胡军",18));
    }
}
