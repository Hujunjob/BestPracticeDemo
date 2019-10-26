package com.hiscene.rxjavastudy.observable;

import android.util.Log;

/**
 * Created by junhu on 2019-10-24
 */
public class ObserverImp implements Observer {
    private static final String TAG = "ObserverImpTAG";
    @Override
    public void change(Object object) {
        System.out.println("观察者观测到: "+object);
    }
}
