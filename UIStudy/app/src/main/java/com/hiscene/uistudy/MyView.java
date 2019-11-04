/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.hiscene.uistudy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by hujun on 2019-11-03.
 */

public class MyView extends View {
    private Paint mPaint;
    private Shader mShader;
    private Bitmap mBitmap;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //float x0, float y0, float x1, float y1, @NonNull @ColorInt int colors[],@Nullable float positions[], @NonNull TileMode tile
//        mShader = new LinearGradient(0,0,500,500,new int[]{Color.RED,Color.BLUE,Color.GREEN},null, Shader.TileMode.CLAMP);

        //RadialGradient()构造方法：
        //float centerX, float centerY, float radius,
        //            @NonNull @ColorInt int colors[], @Nullable float stops[],
        //            @NonNull TileMode tileMode

        //float centerX, float centerY, float radius,
        //            @ColorInt int centerColor, @ColorInt int edgeColor, @NonNull TileMode tileMode


//        mShader = new RadialGradient(250,250,250,new int[]{Color.GREEN,Color.YELLOW,Color.RED},null, Shader.TileMode.CLAMP);

        //SweepGradient()构造方法：
        //float cx, float cy,
        //            @NonNull @ColorInt int colors[], @Nullable float positions[]

        //float cx, float cy, @ColorInt int color0, @ColorInt int color1
//        mShader  = new SweepGradient(250,250,Color.RED,Color.GREEN);

        //BitmapShader()构造方法
        //@NonNull Bitmap bitmap, @NonNull TileMode tileX, @NonNull TileMode tileY


         //ComposeShader()的构造方法：
        //@NonNull Shader shaderA, @NonNull Shader shaderB, @NonNull PorterDuff.Mode mode
        //@NonNull Shader shaderA, @NonNull Shader shaderB, @NonNull Xfermode mode

        mBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.liuyifei);
        BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        LinearGradient linearGradient = new LinearGradient(0,0,500,500,new int[]{Color.RED,Color.BLUE,Color.GREEN},null, Shader.TileMode.CLAMP);

        mShader = new ComposeShader(bitmapShader,linearGradient, PorterDuff.Mode.MULTIPLY);

        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setShader(mShader);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(100,100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0,0,mBitmap.getWidth(),mBitmap.getHeight(),mPaint);
    }


    private void setColor(){
        mPaint = new Paint();
        mPaint.setColor(Color.RED);   //设置颜色，16进制数值，0xFFFF0000
        mPaint.setARGB(0,0,0,0); //分别表示透明度，RGB三原色，0~255数值
        Shader shader = new Shader();
        //一般由以下几种着色器
        mPaint.setShader(shader);   //着色器
        //

        mBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.liuyifei);
        BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        LinearGradient linearGradient = new LinearGradient(0,0,500,500,new int[]{Color.RED,Color.BLUE,Color.GREEN},null, Shader.TileMode.CLAMP);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mShader = new ComposeShader(bitmapShader,linearGradient, new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        mPaint.setShader(mShader);

        //由于某些图层混合在硬件加速下不能用
        //所以需要禁止硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
    }
}
