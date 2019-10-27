package com.hiscene.pattens.adapter;

import io.reactivex.Observable;

/**
 * Created by hujun on 2019-10-27.
 */

public class RxJavaCallAdapterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?, ?> get() {
        return new RxCallAdapter();
    }

    class RxCallAdapter implements CallAdapter {
        @Override
        public Observable adapter(Call call) {
            return null;
        }
    }
}
