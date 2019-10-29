package com.hiscene.opengl;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


public class Main2Activity extends Activity {
    private static final String TAG = "Main2ActivityTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("https://36kr.com/");
        //https://36kr.com/p/5260135
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
//        builder.addConverterFactory()


        Retrofit retrofit = builder.build();

        LoginService loginService = retrofit.create(LoginService.class);
        Call call = loginService.getName();
        Log.d(TAG, "onCreate: ");

    }
}
