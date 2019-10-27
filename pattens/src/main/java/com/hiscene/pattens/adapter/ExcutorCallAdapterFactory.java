package com.hiscene.pattens.adapter;

/**
 * Created by hujun on 2019-10-27.
 */

public class ExcutorCallAdapterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?, ?> get() {
        return new ExcutorCallAdpater();
    }

    class ExcutorCallAdpater implements CallAdapter{

        @Override
        public Call adapter(Call call) {
            return new ExcutorCallback(call);
        }
    }

    static final class ExcutorCallback implements Call{
        private Call call;

        ExcutorCallback(Call call) {
            this.call = call;
        }

        @Override
        public void enqueue() {

        }
    }
}
