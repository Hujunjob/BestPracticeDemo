package com.hiscene.dagger2;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hujun on 2019/4/2.
 */

@Module
public class DaggerModule {
    private final Context context;

    public DaggerModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public C provideC(Context context){
        return new C(context);
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return context;
    }
}
