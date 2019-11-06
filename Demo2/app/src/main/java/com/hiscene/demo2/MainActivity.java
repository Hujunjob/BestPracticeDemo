package com.hiscene.demo2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.hiscene.demo2.eventbus.FunctionHasParamHasResult;
import com.hiscene.demo2.eventbus.FunctionManager;
import com.hiscene.demo2.eventbus.FunctionNoParamNoResult;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivityTAG";
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FunctionManager.getInstance().addFunction(new FunctionNoParamNoResultImp("FunctionNoParamNoResultImp"));
        FunctionManager.getInstance().addFunction(new FunctionHasParamHasResultImp("FunctionHasParamHasResultImp"));

        Intent intent = new Intent();
        intent.setClass(this, Main2Activity.class);
//        startActivity(intent);


        Log.d(TAG, "onCreate: 1");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


        Log.d(TAG, "onCreate: 2");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "run: 1");
                    thread.join();
                    Log.d(TAG, "run: 2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Log.d(TAG, "onCreate: 3");
        t2.start();
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate: 4");
    }

    class FunctionNoParamNoResultImp extends FunctionNoParamNoResult {

        public FunctionNoParamNoResultImp(String funName) {
            super(funName);
        }

        @Override
        public void function() {
            Log.d(TAG, "function: from main activity");
        }
    }

    class FunctionHasParamHasResultImp extends FunctionHasParamHasResult<Integer, String> {

        public FunctionHasParamHasResultImp(String funName) {
            super(funName);
        }

        @Override
        public Integer function(String s) {
            Log.d(TAG, "function: " + s);
            if (s == null) {
                return 0;
            }
            return s.length();
        }
    }

}
