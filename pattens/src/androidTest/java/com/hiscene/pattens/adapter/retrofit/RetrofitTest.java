package com.hiscene.pattens.adapter.retrofit;

import com.hiscene.pattens.adapter.RxJavaCallAdapterFactory;

import org.junit.Test;

import io.reactivex.Observable;

/**
 * Created by hujun on 2019-10-27.
 */

public class RetrofitTest {
    @Test
    public void test(){
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFatory(new RxJavaCallAdapterFactory())
                .build();
        Observable observable = retrofit.create();
    }
}