package com.hiscene.retrofit;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okio.Timeout;

/**
 * Created by junhu on 2019-10-23
 */
public class OKHttpCall implements Call {
    private ServiceMethod serviceMethod;

    private Object[] args;

    private Call call;

    public OKHttpCall(ServiceMethod serviceMethod, Object[] args) {
        this.serviceMethod = serviceMethod;
        this.args = args;
        this.call = serviceMethod.toCall(args);
    }

    @Override
    public void cancel() {

    }

    @NotNull
    @Override
    public Call clone() {
        return call.clone();
    }

    @Override
    public void enqueue(@NotNull Callback callback) {

    }

    @NotNull
    @Override
    public Response execute() throws IOException {
        return null;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public boolean isExecuted() {
        return false;
    }

    @NotNull
    @Override
    public Request request() {
        return call.request();
    }

    @NotNull
    @Override
    public Timeout timeout() {
        return call.timeout();
    }
}
