package com.hiscene.demo2.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by junhu on 2019-10-31
 */
public class IRegularView extends View {
    public IRegularView(Context context) {
        super(context);
    }

    public IRegularView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IRegularView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public IRegularView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Drawable drawable = getBackground();
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

        return super.onTouchEvent(event);
    }
}
