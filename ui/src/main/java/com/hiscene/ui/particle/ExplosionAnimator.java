package com.hiscene.ui.particle;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by junhu on 2019-10-25
 */
public class ExplosionAnimator extends ValueAnimator {
    private Particle[] mParticles;
    private ParticleFactory mParticleFactory;

    private View mContainer;

    public static int defaultDuration = 1000;

    private Paint paint;

    public ExplosionAnimator(ParticleFactory particleFactory, View mContainer, Bitmap bitmap, Rect rect) {
        this.mParticleFactory = particleFactory;
        this.mContainer = mContainer;
        this.paint = new Paint();
        setDuration(defaultDuration);
        setFloatValues(0f,1f);
        mParticles = particleFactory.generateParticles(bitmap,rect);
    }

    public void draw(Canvas canvas){
        if (!isStarted()){
            //可能是还没有开始，可能已经结束
            return;
        }

        for (Particle mParticle : mParticles) {
            mParticle.advance(canvas,paint, (Float) getAnimatedValue());
        }
        mContainer.invalidate();
    }

    @Override
    public void start() {
        super.start();
        mContainer.invalidate();
    }
}
