package com.hiscene.pattens.adapter.retrofit;

import com.hiscene.pattens.adapter.Call;
import com.hiscene.pattens.adapter.CallAdapter;

/**
 * Created by hujun on 2019-10-27.
 */

public class ServiceMethod<R,T> {
    private CallAdapter<R,T>  callAdapter;

    T adapt(Call<R> call){
        return callAdapter.adapter(call);
    }

    private ServiceMethod(Builder<R,T> builder){
        callAdapter = builder.callAdapter;
    }

    static final class Builder<R,T>{
        private Retrofit retrofit;
        CallAdapter<R,T> callAdapter;

        public Builder(Retrofit retrofit) {
            this.retrofit = retrofit;
        }

        public ServiceMethod build(){
            callAdapter = createCallAdapter();
            return new ServiceMethod<>(this);
        }

        private CallAdapter<R, T> createCallAdapter() {
            return (CallAdapter<R, T>) retrofit.callAdapter();
        }
    }
}
