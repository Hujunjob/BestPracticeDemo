package com.hiscene.arcore;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.CameraNotAvailableException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class ARImageActivity extends Activity {
    private static final String TAG = "ARImageActivityTAG";
    private AugmentedImageDatabase augmentedImageDatabase;
    private SparseArray<String> imageMap = new SparseArray<>();
    private Session arSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arimage);
        arSession = ((AppApplication)getApplication()).getArSession();
        augmentedImageDatabase = new AugmentedImageDatabase(arSession);
        addImage();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        try {
            arSession.resume();
        } catch (CameraNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        arSession.pause();
    }

    private void addImage(){
        new Thread(() -> {
            Bitmap bitmap;
            try {
                InputStream inputStream = getAssets().open("earth.jpg");
                bitmap = BitmapFactory.decodeStream(inputStream);
                int index = augmentedImageDatabase.addImage("earth",bitmap);
                imageMap.put(index,"earth");
                startRecognize();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void startRecognize(){
        Log.d(TAG, "startRecognize: ");
        Config config = arSession.getConfig();
        config.setAugmentedImageDatabase(augmentedImageDatabase);
        arSession.configure(config);
    }

    private void checkImageUpdate(){
        try {
            Frame frame = arSession.update();
            Collection<AugmentedImage> augmentedImages = frame.getUpdatedTrackables(AugmentedImage.class);
            for (AugmentedImage image:augmentedImages){
                String name = image.getName();
                Log.d(TAG, "checkImageUpdate: "+name);
                if (image.getTrackingState() == TrackingState.TRACKING){
                    Log.d(TAG, "checkImageUpdate: index="+image.getIndex());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
