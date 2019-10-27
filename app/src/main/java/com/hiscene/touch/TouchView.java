package com.hiscene.touch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by junhu on 2019-10-18
 */
public class TouchView extends ImageView {
    private static final String TAG = "TouchViewTAG";
    public TouchView(Context context) {
        super(context);
        Log.d(TAG, "TouchView: 1");
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "TouchView: 2");
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: "+event.getAction());
                return false;
            }
        });
//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: ");
//            }
//        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent: "+event.getAction());
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: "+event.getAction());
        return super.onTouchEvent(event);
    }
}
