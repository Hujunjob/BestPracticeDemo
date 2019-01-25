package com.hiscene.arcore;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.UnavailableException;
import com.google.ar.sceneform.ArSceneView;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MainActivityTAG";
    private Session arSession;
    private ArSceneView arSceneView;

    private boolean installRequested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_check).setOnClickListener(this);

        arSceneView = findViewById(R.id.ar_view);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (arSceneView.getSession() == null) {
            // If the session wasn't created yet, don't resume rendering.
            // This can happen if ARCore needs to be updated or permissions are not granted yet.
            try {
                Session session = DemoUtils.createArSession(this, installRequested);
                if (session == null) {
                    installRequested = DemoUtils.hasCameraPermission(this);
                    return;
                } else {
                    arSceneView.setupSession(session);
                }
            } catch (UnavailableException e) {
                DemoUtils.handleSessionException(this, e);
            }
        }

        try {
            arSceneView.resume();
        } catch (CameraNotAvailableException ex) {
            DemoUtils.displayError(this, "Unable to get camera", ex);
            finish();
            return;
        }

        if (arSceneView.getSession() != null) {
//            showLoadingMessage();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_check: {
                checkArCore();
            }
            break;
            default:
                break;
        }
    }

    private void checkArCore() {
        Log.d(TAG, "checkArCore");
        ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(this);
        if (availability == ArCoreApk.Availability.UNKNOWN_ERROR) {
            Toast.makeText(this, "ARCore 功能不能用", Toast.LENGTH_SHORT).show();
            checkArCoreInstall();
            return;
        }
        if (availability.isTransient()) {
            Log.d(TAG, "checkArCore: isTransient");
            new Handler().postDelayed(this::checkArCore, 200);
        }

        if (availability.isSupported()) {
            Log.d(TAG, "checkArCore: isSupported");
            checkArCoreInstall();
        }
    }

    private void checkArCoreInstall() {
        try {
            ArCoreApk.InstallStatus status = ArCoreApk.getInstance().requestInstall(this, false);
            switch (status) {
                case INSTALLED:
                    arSession = new Session(this);
                    toast("ARCore installed");
                    break;
                case INSTALL_REQUESTED:
                    toast("没有安装ArCore");
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
}
