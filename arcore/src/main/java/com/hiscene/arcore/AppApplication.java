package com.hiscene.arcore;

import android.app.Application;

import com.google.ar.core.Session;

/**
 * Created by hujun on 2018/12/17.
 */

public class AppApplication extends Application {
    private Session arSession;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Session getArSession(){
        return arSession;
    }

    public void setArSession(Session session){
        arSession = session;
    }
}
