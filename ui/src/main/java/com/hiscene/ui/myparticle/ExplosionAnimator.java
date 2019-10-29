package com.hiscene.ui.myparticle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.hiscene.ui.R;

/**
 * Created by junhu on 2019-10-26
 */
public class ExplosionAnimator extends ValueAnimator {
    private View mView;
    private View mContainer;
    private ParticleFactory particleFactory;
    private Particle[] particles;
    private Paint paint;

    public static int defaultDuration = 1000;

    public ExplosionAnimator(View mView, View mContainer, ParticleFactory factory) {
        this.mView = mView;
        this.mContainer = mContainer;
        particleFactory = factory;

        this.paint = new Paint();
        initAnimator();
    }

    private void initAnimator() {
        setFloatValues(0, 1);
        setDuration(defaultDuration);
        addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mView.animate().setDuration(150).alpha(1).scaleX(1).scaleY(1).start();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mView.animate().setDuration(150).alpha(0).scaleX(0).scaleY(0).start();
            }
        });
    }

    private void initParticles() {
        Rect rect = new Rect();
        mView.getGlobalVisibleRect(rect);
        Bitmap bitmap = getBitmapByView(mView);
        particles = particleFactory.generateParticle(bitmap, rect);
    }

    private Bitmap getBitmapByView(View view) {
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
//        view.destroyDrawingCache();
        return bitmap;
    }

    public void draw(Canvas canvas) {
        if (!isStarted()) {
            return;
        }
        if (particles != null) {
            for (Particle particle : particles) {
                particle.refresh(canvas, paint, (Float) getAnimatedValue());
            }
            mContainer.invalidate();
        }
    }

    public void onClicked() {
        //view被点击，则需要跳动，然后触发爆炸效果
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1f).setDuration(150);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //view震动
                mView.setTranslationX((float) ((Math.random() - 0.5) * mView.getWidth() * 0.5));
                mView.setTranslationY((float) ((Math.random() - 0.5) * mView.getHeight() * 0.5));
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ExplosionAnimator.this.start();
                mView.setTranslationX(0);
                mView.setTranslationY(0);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        animator.start();
    }

    @Override
    public void start() {
        super.start();
        initParticles();
        mContainer.invalidate();
    }
}
