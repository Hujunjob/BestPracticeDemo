package com.hiscene.ui.particle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by junhu on 2019-10-25
 */
public class FallingParticle extends Particle {
    float radius = FallingFactory.PARTICLE_SIZE;
    float alpha = 1f;

    public FallingParticle(float cx, float cy, int color,Rect rect) {
        super(cx, cy, color,rect);
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (alpha* Color.alpha(color)));
        canvas.drawCircle(cx,cy,radius,paint);
    }

    @Override
    protected void calculate(float factor) {
        cx = (float) (cx + factor * Math.random() * mBound.width()*(Math.random()-0.5));
        cy = (float) (cy + factor * Math.random() * mBound.height() * 0.5);

        radius = (float) (radius - factor * Math.random());
        alpha = (float) ((1-factor) * (1+Math.random()));
    }
}
