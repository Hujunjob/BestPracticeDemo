package com.hiscene.opengl;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hiscene.utils.UrlParseUtils;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener {
    static {
        System.loadLibrary("native-lib");
    }

    private static final String TAG = "MainActivityTAG";

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final int REQUEST_CODE = 677;
    public static final int INSTALL_PACKAGES_REQUEST_CODE = 397;
    public static final int GET_UNKNOWN_APP_SOURCES = 311;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: " + stringFromJNI());

        findViewById(R.id.btn_pitch).setOnClickListener(this);

        checkPermissions();
        checkIsAndroidO();
        TextView textView = findViewById(R.id.txt_version);
        textView.setText(BuildConfig.VERSION_NAME);
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permissions, REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case INSTALL_PACKAGES_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    mMainPresenter.installApk();
                } else {
                    //  引导用户手动开启安装权限
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                    startActivityForResult(intent, GET_UNKNOWN_APP_SOURCES);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        new AsyncTask<Void, Void, File>() {
            //耗时操作，返回File文件，是合成后的新版apk文件
            @Override
            protected File doInBackground(Void... voids) {
                //从私有路径中获取用户现有使用的app的apk文件
                String oldApk = getApplicationInfo().sourceDir;

                String patch = new File("/sdcard/patch").getAbsolutePath();

                String output = createNewApk().getAbsolutePath();

                patch(oldApk, patch, output);
                return new File(output);
            }

            @Override
            protected void onPostExecute(File file) {
                //获得合成后的新apk，安装
                UrlParseUtils.installApk(MainActivity.this, file);
            }
        }.execute();
    }

    /*
     *
     * 判断是否是8.0,8.0需要处理未知应用来源权限问题,否则直接安装
     */
    private void checkIsAndroidO() {
        if (Build.VERSION.SDK_INT >= 26) {
            boolean b = getPackageManager().canRequestPackageInstalls();
            if (b) {
//                mMainPresenter.installApk();
            } else {
                //请求安装未知应用来源的权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_PACKAGES_REQUEST_CODE);
            }
        } else {
//            mMainPresenter.installApk();
        }
    }

    private File createNewApk() {
        File newApk = new File(Environment.getExternalStorageDirectory(), "bsdiff.apk");
        try {
            if (!newApk.exists()) {
                newApk.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newApk;
    }

    public native String stringFromJNI();


    public native void patch(String oldApk, String patch, String output);
}
