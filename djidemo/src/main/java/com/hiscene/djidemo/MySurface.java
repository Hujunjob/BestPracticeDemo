package com.hiscene.djidemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hiscene.utils.CameraManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hujun on 2019/4/10.
 */

public class MySurface extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "MySurfaceTAG";
    private CameraManager cameraManager;
    private Paint mPaint;
    private Paint txtPaint;

    private int timeCount;

    public MySurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        cameraManager = CameraManager.getInstance(context);
        getHolder().addCallback(this);
        initPaint();
        startTimer();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL);

        txtPaint = new Paint();
        txtPaint.setStrokeWidth(5);//设置画笔宽度
        txtPaint.setAntiAlias(false);//设置是否使用抗锯齿功能，如果使用，会导致绘图速度变慢
        txtPaint.setStyle(Paint.Style.FILL);//设置绘图样式，对于设置文字和几何图形都有效，可取值有三种 ：1、Paint.Style.FILL：填充内部 2、Paint.Style.FILL_AND_STROKE：填充内部和描边 3、Paint.Style.STROKE：仅描边
        txtPaint.setTextAlign(Paint.Align.CENTER);//设置文字对齐方式
        txtPaint.setTextSize(32);//设置文字大小
//        txtPaint.setFakeBoldText(true);//设置是否为粗体文字
//        txtPaint.setUnderlineText(true);//设置下划线
//        txtPaint.setTextSkewX((float) -0.25);//设置字体水平倾斜度，普通斜体字是 -0.25
//        txtPaint.setStrikeThruText(true);//设置带有删除线效果
        txtPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: ");
        canvas.drawRect(100, 100, 200, 200, mPaint);
        String timeStr = "Seconds:" + timeCount;
        canvas.drawText(timeStr, 100, 300, txtPaint);
    }

    private void startTimer() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                timeCount++;
                postInvalidate();
            }
        }, 1000, 1000);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setWillNotDraw(false);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        cameraManager.setHolder(holder);
        try {
            cameraManager.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cameraManager.close();
    }
}
