package com.hiscene.camera;

import android.Manifest;
import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.view.SurfaceView;

import com.hiscene.arcore.R;

import java.io.IOException;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class CameraActivity extends Activity {
    public static final int CAMERA_CODE = 658;
    private Camera camera;
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        checkPermission();

        surfaceView = findViewById(R.id.surface_view);

        findViewById(R.id.btn_open).setOnClickListener(view -> {
            openCamera();
        });
    }

    private void openCamera(){
        int num = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo0 = new Camera.CameraInfo();
        Camera.getCameraInfo(0,cameraInfo0);
//        Camera.CameraInfo cameraInfo1 = new Camera.CameraInfo();
//        Camera.getCameraInfo(1,cameraInfo1);
        if (camera==null){
            camera = Camera.open(0);
        }

        try {
            camera.setPreviewDisplay(surfaceView.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (camera!=null){
            camera.stopPreview();
            camera.release();
        }
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_CODE) {
            if (grantResults[0] != PERMISSION_GRANTED) {
                finish();
            }
        }
    }

}
