package com.hiscene.uistudy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by hujun on 2019-11-03.
 */

public class XfermodeView extends View {
    private Paint mPaint;

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        setBackgroundColor(Color.RED);

    }

    private void init() {
        mPaint = new Paint();

        //禁止硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //离屏绘制
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint);

        canvas.drawBitmap(createCircleBitmap(getWidth(),getHeight()),0,0,mPaint);

        canvas.restoreToCount(layerId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(500,500);
    }

    private Bitmap createRectBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(0xFF66AAFF);
        paint.setAntiAlias(true);
        canvas.drawRect(0, 0, width * 0.5f, height * 0.5f, paint);
        return bitmap;
    }

    private Bitmap createCircleBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(0xFFFFCC44);
        paint.setAntiAlias(true);
        canvas.drawCircle(width * 0.5f, height * 0.5f, width * 0.3f, paint);
        return bitmap;
    }


}
