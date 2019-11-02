package com.hiscene.ui.particle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by junhu on 2019-10-25
 */
public abstract class Particle {
    float cx;
    float cy;
    int color;
    Rect mBound;

    public Particle(float cx, float cy, int color, Rect rect) {
        this.cx = cx;
        this.cy = cy;
        this.color = color;
        this.mBound = rect;
    }

    //逐帧绘制
    public void advance(Canvas canvas, Paint paint,float factor){
        calculate(factor);
        draw(canvas,paint);
    }

    protected abstract void draw(Canvas canvas, Paint paint);

    protected abstract void calculate(float factor);
}
