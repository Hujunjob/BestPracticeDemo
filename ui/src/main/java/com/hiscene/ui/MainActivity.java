package com.hiscene.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.hiscene.ui.particle.ExposionField;
import com.hiscene.ui.particle.FallingFactory;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = getWindow().getDecorView();
        int param = View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(param);


        ExposionField exposionField = new ExposionField(this,new FallingFactory());
//        exposionField.addExpostion(findViewById(R.id.image));
        exposionField.addExpostion(findViewById(R.id.cl_container));
    }
}
