package com.hiscene.demo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hiscene.demo2.eventbus.FunctionManager;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2ActivityTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FunctionManager.getInstance().invokeFunction("FunctionNoParamNoResultImp");
            }
        });

        findViewById(R.id.btn_test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer integer = FunctionManager.getInstance().<Integer,String>invokeFunction("FunctionHasParamHasResultImp","hello",Integer.class);
                Log.d(TAG, "invokeFunction: "+integer);
            }
        });
    }
}
