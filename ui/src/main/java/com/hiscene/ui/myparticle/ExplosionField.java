package com.hiscene.ui.myparticle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by junhu on 2019-10-26
 */
public class ExplosionField extends View implements View.OnClickListener {
    private ParticleFactory particleFactory;
    private Map<View, ExplosionAnimator> animatorMap = new HashMap<>();

    public ExplosionField(Context context, ParticleFactory particleFactory) {
        super(context);
        ViewGroup viewGroup = (ViewGroup) ((Activity) context).getWindow().getDecorView();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewGroup.addView(this, params);
        this.particleFactory = particleFactory;
    }

    public void addExplodeView(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View view1 = viewGroup.getChildAt(i);
                addExplodeView(view1);
            }
        } else {
            view.setClickable(true);
            view.setOnClickListener(this);
            ExplosionAnimator animator = new ExplosionAnimator(view, this, particleFactory);
            animatorMap.put(view, animator);
        }
    }

    public void removeExplodeView(View view) {
        animatorMap.remove(view);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Map.Entry<View, ExplosionAnimator> set : animatorMap.entrySet()) {
            set.getValue().draw(canvas);
        }
    }

    @Override
    public void onClick(View view) {
        //view被点击
        ExplosionAnimator animator = animatorMap.get(view);
        if (animator != null) {
            animator.onClicked();
        }
    }
}
