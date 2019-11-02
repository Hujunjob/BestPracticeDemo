package com.hiscene.touch;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by junhu on 2019-10-18
 */
public class TouchLayout extends ConstraintLayout {
    public TouchLayout(Context context) {
        super(context);
    }

    public TouchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
