package com.hiscene.pattens.adapter.retrofit;

import com.hiscene.pattens.adapter.CallAdapter;
import com.hiscene.pattens.adapter.ExcutorCallAdapterFactory;
import com.hiscene.pattens.adapter.OkHttpCall;

/**
 * Created by hujun on 2019-10-27.
 */

public class Retrofit {
    private CallAdapter.Factory factory;

    private Retrofit(CallAdapter.Factory factory) {
        this.factory = factory;
    }

    public CallAdapter<?,?> callAdapter(){
        return factory.get();
    }

    public <T> T create(){
        ServiceMethod serviceMethod = new ServiceMethod.Builder<>(this).build();
        OkHttpCall<Object> okHttpCall = new OkHttpCall<>();
        T adapt = (T) serviceMethod.adapt(okHttpCall);
        return adapt;
    }

    public static final class Builder{
        CallAdapter.Factory factory;

        public Builder addCallAdapterFatory(CallAdapter.Factory factory){
            this.factory = factory;
            return this;
        }

        public Retrofit build(){
            if (factory == null){
                factory = new ExcutorCallAdapterFactory();
            }
            return new Retrofit(factory);
        }
    }
}
