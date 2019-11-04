package com.hiscene.uistudy;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;

public class MainActivity extends Activity {
    private Paint mPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        ViewGroup decorView = (ViewGroup) window.getDecorView();
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN);
        int child = decorView.getChildCount();
        ViewGroup contentView = (ViewGroup) decorView.getChildAt(0);
//        findViewById(R.id.txt).setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN);
//        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN|WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

    }


}
