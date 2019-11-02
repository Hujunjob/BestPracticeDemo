package com.hiscene.handdetection;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import org.tensorflow.demo.tracking.MultiBoxTracker;


public class MainActivity extends Activity {
    private static final String TAG = "MainActivityTAG";
    private GestureSurface gestureSurface;
    private CameraManager cameraManager;

    private MultiBoxTracker tracker;

    private int orientation=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        gestureSurface = findViewById(R.id.gesture_surface);
        gestureSurface.setSurfaceCallback(surfaceCallback);
        findViewById(R.id.btn_start).setOnClickListener(view -> {
            cameraManager.setPreview(gestureSurface.getHolder());
//            new Thread(() -> cameraManager.open()).start();
            cameraManager.newOpenCamera();
        });
        cameraManager = new CameraManager();
        cameraManager.setCallback(previewCallback);
        checkCamera();

        tracker = new MultiBoxTracker(this);
        tracker.addResultsBack(hdResultBack);
    }

    private void checkCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    private MultiBoxTracker.HDResultBack hdResultBack = (name, pos) -> {

    };

    private GestureSurface.SurfaceCallback surfaceCallback = canvas -> {
//        tracker.draw(canvas);
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }



    private Camera.PreviewCallback previewCallback = (bytes, camera) -> {
        Log.d(TAG, "PreviewCallback");
//        tracker.onFrame(cameraManager.getWidth(), cameraManager.getHeight(),
//                cameraManager.getWidth(),orientation,
//                bytes,System.currentTimeMillis());
    };


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraManager.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraManager.release();
    }
}
