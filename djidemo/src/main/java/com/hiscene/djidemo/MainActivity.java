package com.hiscene.djidemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {
    private static final String TAG = "MainActivityTAG";
    private MySurface mySurface;
    private SurfaceView cameraSurface;
//    private CameraManager cameraManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
//        checkCamera(this);
        mySurface = findViewById(R.id.my_surface);
        cameraSurface = findViewById(R.id.surface_camera);

//        cameraManager = CameraManager.getInstance(this);
//        cameraManager.setHolder(cameraSurface.getHolder());
        try {
//            cameraManager.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public static void checkCamera(Activity activity) {
//        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1);
//        }
//    }
}
