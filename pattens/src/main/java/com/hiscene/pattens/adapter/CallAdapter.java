package com.hiscene.pattens.adapter;

/**
 * Created by hujun on 2019-10-27.
 */

public interface CallAdapter<R,T> {
    T adapter(Call<R> call);

    abstract class Factory{
        public abstract CallAdapter<?,?> get();
    }
}
