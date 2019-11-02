package com.hiscene.ui.myparticle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by junhu on 2019-10-26
 */
public abstract class Particle {
    float cx;
    float cy;
    int color;
    int alpha;

    public Particle(float cx, float cy, int color) {
        this.cx = cx;
        this.cy = cy;
        this.color = color;
        this.alpha = Color.alpha(color);
    }

    public void refresh(Canvas canvas, Paint paint, float factor) {
        calculate(factor);
        draw(canvas, paint);
    }

    public abstract void draw(Canvas canvas, Paint paint);

    public abstract void calculate(float factor);
}
