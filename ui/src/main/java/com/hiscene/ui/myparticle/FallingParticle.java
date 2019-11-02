package com.hiscene.ui.myparticle;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by junhu on 2019-10-26
 */
public class FallingParticle extends Particle {
    private float radius;

    public FallingParticle(float cx, float cy, int color,int radius) {
        super(cx, cy, color);
        this.radius = radius;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha(alpha);
        canvas.drawCircle(cx,cy,radius,paint);
    }

    @Override
    public void calculate(float factor) {
        cx = (float) (cx + factor * Math.random() * 10*(Math.random()-0.5));
        cy = (float) (cy + factor * Math.random() * 10 * 0.5);

        radius = (float) (radius - factor * Math.random());
        alpha = (int) ((1-factor) * (1+Math.random()));
    }
}
