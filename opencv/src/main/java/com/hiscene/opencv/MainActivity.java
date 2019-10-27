package com.hiscene.opencv;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.hiscene.dagger2.A;
import com.hiscene.dagger2.D;
import com.hiscene.dagger2.DaggerMatchComponent;
import com.hiscene.dagger2.DaggerModule;
import com.hiscene.dagger2.MatchComponent;

import javax.inject.Inject;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivityTAG";
    private TextView text;

    @Inject
    A a;

    @Inject
    D d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text1);

        MatchComponent component = DaggerMatchComponent.builder().daggerModule(new DaggerModule(this)).build();
        component.mainInject(this);

        a.birth();
        a.hello();
        d.hello();
    }


}
