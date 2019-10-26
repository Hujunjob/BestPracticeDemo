package com.hiscene.opengl;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit.Builder builder = new Retrofit.Builder();

    }

    @Override
    public void onClick(View view) {

    }
}
