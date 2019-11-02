package com.hiscene.ui.particle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junhu on 2019-10-25
 * 动画场地
 */
public class ExposionField extends View {
    private static final String TAG = "ExposionFieldTAG";
    //可以同时执行多个动画
    private List<ExplosionAnimator> animators = new ArrayList<>();

    //粒子工厂，可能同时会有不同粒子效果
    private ParticleFactory mParticleFactory;

    //需要实现粒子效果的控件
    private View.OnClickListener onClickListener;


    public ExposionField(Context context, ParticleFactory particleFactory) {
        super(context);
        mParticleFactory = particleFactory;

        //如何将场地绑定到界面里
        attach2Activity();
    }

    private void attach2Activity() {
        //将场地添加到DecorView中
        ViewGroup decorView = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        decorView.addView(this, params);
    }

    public void addExpostion(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int childNum = group.getChildCount();
            for (int i = 0; i < childNum; i++) {
                addExpostion(group.getChildAt(i));
            }
        } else {
            view.setClickable(true);
            view.setOnClickListener(getOnClickListener());
        }
    }

    private OnClickListener getOnClickListener() {
        if (onClickListener == null) {
            onClickListener = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //触发动画执行
                    viewClick(view);
                }
            };
        }
        return onClickListener;
    }


    private void viewClick(final View view) {
        if (view.getVisibility() != VISIBLE || view.getAlpha() != 1) {
            return;
        }

        //获取控件位置
        final Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        //如果控件在屏幕边缘不可见，则不爆炸
        if (rect.width() == 0 || rect.height() == 0) {
            return;
        }

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1).setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //改变控件位置
                view.setTranslationX(((float) Math.random() - 0.5f) * view.getWidth() * 0.05f);
                view.setTranslationY(((float) Math.random() - 0.5f) * view.getHeight() * 0.05f);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setTranslationX(0);
                view.setTranslationY(0);
                expose(view,rect);
            }
        });
        animator.start();
    }

    //开始爆炸
    private void expose(final View view, Rect rect) {
        //首先需要拿到view的bitmap
        final ExplosionAnimator explosionAnimator = new ExplosionAnimator(mParticleFactory,this,createBitmap(view),rect);
        animators.add(explosionAnimator);
        explosionAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.animate().setDuration(150).scaleX(1).scaleY(1).alpha(1).start();
                animators.remove(explosionAnimator);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.animate().setDuration(150).scaleX(0).scaleY(0).alpha(0).start();
            }
        });
        explosionAnimator.start();
    }

    private Bitmap createBitmap(View view){
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: ");

        for (ExplosionAnimator animator : animators) {
            animator.draw(canvas);
        }
    }
}
