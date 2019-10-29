package com.hiscene.utils;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import static android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO;

/**
 * Created by hujun on 2019/4/10.
 */

public class CameraManager {
    private Camera camera;
    private Context mContext;
    private static CameraManager instance;

    private SurfaceTexture surfaceTexture;
    private SurfaceHolder holder;

    private Camera.PreviewCallback callback;

    private int width = 1280;
    private int height = 720;

    public static CameraManager getInstance(Context context) {
        if (instance == null) {
            instance = new CameraManager(context);
        }

        return instance;
    }

    private CameraManager(Context mContext) {
        this.mContext = mContext;
    }

    public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        this.surfaceTexture = surfaceTexture;
    }

    public void setHolder(SurfaceHolder holder) {
        this.holder = holder;
    }

    public void open() throws Exception {
        if (camera == null) {
            camera = Camera.open();
        }
        if (surfaceTexture != null) {
            camera.setPreviewTexture(surfaceTexture);
        } else if (holder != null) {
            camera.setPreviewDisplay(holder);
        } else {
            SurfaceTexture texture = new SurfaceTexture(-1);
            camera.setPreviewTexture(texture);
        }

        Camera.Parameters parameters = camera.getParameters();
        parameters.setPreviewSize(width, height);
        if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }else if (parameters.getSupportedFocusModes().contains(FOCUS_MODE_CONTINUOUS_VIDEO)){
            parameters.setFocusMode(FOCUS_MODE_CONTINUOUS_VIDEO);
        }
        camera.setParameters(parameters);

        int size = width * height * ImageFormat.getBitsPerPixel(ImageFormat.NV21);
        for (int i = 0; i < 3; i++) {
            byte[] buffre = new byte[size];
            camera.addCallbackBuffer(buffre);
        }

        camera.setPreviewCallbackWithBuffer(previewCallback);
        camera.startPreview();
    }

    public void close() {
        if (camera != null) {
            camera.stopPreview();
        }
    }

    public void release() {
        if (camera != null) {
            camera.release();
        }
    }

    public void setPreviewCallback(Camera.PreviewCallback callback){
        this.callback =callback;
    }

    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            if (callback != null) {
                callback.onPreviewFrame(data,camera);
            }
            camera.addCallbackBuffer(data);
        }
    };
}
