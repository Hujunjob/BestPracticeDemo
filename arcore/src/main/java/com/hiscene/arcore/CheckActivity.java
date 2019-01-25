package com.hiscene.arcore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Session;

public class CheckActivity extends Activity {
    private static final int RC_PERMISSIONS = 0x123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        DemoUtils.requestCameraPermission(this, RC_PERMISSIONS);
        checkARCore();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void checkARCore() {
        ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(this);
        if (availability == ArCoreApk.Availability.UNKNOWN_ERROR) {
            Toast.makeText(this, "ARCore 功能不能用", Toast.LENGTH_SHORT).show();
            checkARCoreInstall();
            return;
        }

        if (availability.isTransient()){
            new Handler().postDelayed(this::checkARCore,200);
        }

        if (availability.isSupported()){
            checkARCoreInstall();
        }
    }


    private void checkARCoreInstall() {
        try {
            ArCoreApk.InstallStatus status = ArCoreApk.getInstance().requestInstall(this, false);
            switch (status) {
                case INSTALL_REQUESTED:
                    toast("没有安装ARCore");
                    break;
                case INSTALLED:
                    toast("已经安装ARCore");
                    ((AppApplication)getApplication()).setArSession(new Session(this));
                    gotoMainActivity();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void gotoMainActivity(){
        Intent intent = new Intent(this,ARImageActivity.class);
        startActivity(intent);
    }
}
