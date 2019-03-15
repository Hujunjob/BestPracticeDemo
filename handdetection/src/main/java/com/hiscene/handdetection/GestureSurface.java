package com.hiscene.handdetection;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by hujun on 2019/3/2.
 */

public class GestureSurface extends SurfaceView {
    public GestureSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (surfaceCallback != null) {
            surfaceCallback.onDraw(canvas);
        }

    }

    public interface SurfaceCallback{
        void onDraw(Canvas canvas);
    }

    private SurfaceCallback surfaceCallback;

    public void setSurfaceCallback(SurfaceCallback surfaceCallback) {
        this.surfaceCallback = surfaceCallback;
    }
}
