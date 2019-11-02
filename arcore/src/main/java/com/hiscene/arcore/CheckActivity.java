package com.hiscene.arcore;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Session;
import com.hiscene.utils.PermissionPageUtils;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class CheckActivity extends Activity {
    private static final String TAG = "CheckActivityTAG";
    private static final int RC_PERMISSIONS = 0x123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        DemoUtils.requestCameraPermission(this, RC_PERMISSIONS);
        checkARCore();
        findViewById(R.id.btn_set).setOnClickListener(view -> {
            PermissionPageUtils utils = new PermissionPageUtils(CheckActivity.this);
            utils.jumpPermissionPage();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            Log.d(TAG, "onRequestPermissionsResult: permission=" + permissions[i] + ",code=" + grantResults[i] + ",requestcode=" + requestCode);
            if (grantResults[i] != PERMISSION_GRANTED) {
                ActivityCompat.shouldShowRequestPermissionRationale(CheckActivity.this, Manifest.permission.CAMERA);
            }
        }

    }

    private void checkARCore() {
        ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(this);
        if (availability == ArCoreApk.Availability.UNKNOWN_ERROR) {
            Toast.makeText(this, "ARCore 功能不能用", Toast.LENGTH_SHORT).show();
            checkARCoreInstall();
            return;
        }

        if (availability.isTransient()) {
            new Handler().postDelayed(this::checkARCore, 200);
        }

        if (availability.isSupported()) {
            checkARCoreInstall();
        }
    }


    private void checkARCoreInstall() {
        try {
            ArCoreApk.InstallStatus status = ArCoreApk.getInstance().requestInstall(this, true);
            switch (status) {
                case INSTALL_REQUESTED:
                    toast("没有安装ARCore");
                    break;
                case INSTALLED:
                    toast("已经安装ARCore");
                    ((AppApplication) getApplication()).setArSession(new Session(this));
                    gotoMainActivity();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "checkARCoreInstall: " + e.getMessage());
            toast("不支持的设备");
        }
    }


    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void gotoMainActivity() {
        Log.d(TAG, "gotoMainActivity: ");
//        Intent intent = new Intent(this, ARImageActivity.class);
//        startActivity(intent);
    }
}
