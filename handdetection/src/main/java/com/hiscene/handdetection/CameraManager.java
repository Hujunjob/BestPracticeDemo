package com.hiscene.handdetection;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.List;

import static android.graphics.ImageFormat.NV21;

/**
 * Created by hujun on 2019/3/2.
 */

public class CameraManager {
    private static final String TAG = "CameraManagerTAG";
    private Camera camera;
    private SurfaceHolder surfaceHolder;

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public void setPreview(SurfaceHolder holder) {
        this.surfaceHolder = holder;
    }

    public void open() {
        if (camera == null) {
            camera = Camera.open(0);
        }
        if (surfaceHolder != null) {
            try {
                camera.setPreviewDisplay(surfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return;
        }

        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();

        parameters.setPreviewSize(WIDTH, HEIGHT);
//        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        parameters.setPreviewFormat(NV21);
//        parameters.setPreviewFpsRange(30*1000,30*1000);
//        parameters.setPreviewFrameRate(30);
        int bufferSize = WIDTH * HEIGHT * ImageFormat.getBitsPerPixel(NV21) / 8;
        for (int i = 0; i < 3; i++) {
            byte[] buffer = new byte[bufferSize];
            camera.addCallbackBuffer(buffer);
        }

        camera.setPreviewCallbackWithBuffer(previewCallback);
        camera.setParameters(parameters);
        camera.setDisplayOrientation(90);
        camera.startPreview();
    }


    public void pause() {
        if (camera != null) {
            camera.stopPreview();
        }
    }

    public void release() {
        if (camera != null) {
            camera.release();
        }
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    private Camera.PreviewCallback callback;

    public void setCallback(Camera.PreviewCallback previewCallback) {
        callback = previewCallback;
    }

    private Camera.PreviewCallback previewCallback = (bytes, camera) -> {
        if (callback != null) {
            callback.onPreviewFrame(bytes, camera);
        }
        camera.addCallbackBuffer(bytes);
    };

    public void newOpenCamera() {
        if (mThread == null) {
            mThread = new CameraHandlerThread();
        }

        synchronized (mThread) {
            mThread.openCamera();
        }
    }
    private CameraHandlerThread mThread = null;
    private  class CameraHandlerThread extends HandlerThread {
        Handler mHandler = null;

        CameraHandlerThread() {
            super("CameraHandlerThread");
            start();
            mHandler = new Handler(getLooper());
        }

        synchronized void notifyCameraOpened() {
            notify();
        }

        void openCamera() {
            mHandler.post(() -> {
//                oldOpenCamera();
                CameraManager.this.open();
                notifyCameraOpened();
            });
            try {
                wait();
            }
            catch (InterruptedException e) {
                Log.w(TAG, "wait was interrupted");
            }
        }
    }

}
