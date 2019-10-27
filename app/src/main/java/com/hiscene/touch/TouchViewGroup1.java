package com.hiscene.touch;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by junhu on 2019-10-18
 */
public class TouchViewGroup1 extends ConstraintLayout {
    private static final String TAG = "TouchViewGroup1";
    public TouchViewGroup1(Context context) {
        super(context);
    }

    public TouchViewGroup1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchViewGroup1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent: "+event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: "+event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent: "+ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }
}
