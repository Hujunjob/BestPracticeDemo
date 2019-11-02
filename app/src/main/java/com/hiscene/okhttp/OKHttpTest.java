package com.hiscene.okhttp;

import android.os.Environment;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RealCall;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by junhu on 2019-10-25
 */
public class OKHttpTest {
    private static final String TAG = "OKHttpTestTAG";
    public void get(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoginInterceptor())
                .build();
        Request.Builder builder = new Request.Builder().url("");
        Request request = builder.build();

        try {
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            if (response.isSuccessful()){
                ResponseBody body = response.body();
                if (body!=null) {
                    InputStream inputStream = body.byteStream();
                }
            }else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void post(){
        OkHttpClient okHttpClient = new OkHttpClient();
        String json  ="";
        RequestBody requestBody = RequestBody.create(MediaType.parse(""),json);
        Request.Builder builder = new Request.Builder();
        builder.url("").post(requestBody);
        Request request = builder.build();
        try {
            Response response =okHttpClient.newCall(request).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class LoginInterceptor implements Interceptor {

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            return null;
        }
    }

    public void responseCaching(){
        String url = "https://publicobject.com";
        File file = new File("/sdcard/cache");
        int cacheSize = 10 * 1024*1024;
        Cache cache = new Cache(file,cacheSize);
        OkHttpClient httpClient = new OkHttpClient.Builder().cache(cache).connectTimeout(10,TimeUnit.SECONDS).build();

        CacheControl cacheControl = new CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(url).cacheControl(cacheControl).build();


        Call call1 = httpClient.newCall(request);
        Call call2 = httpClient.newCall(request);

        try {
            Response response = call1.execute();
            response.body().close();
            if (response.isSuccessful()){
                Log.d(TAG, "responseCaching cache: "+response.cacheResponse());
                Log.d(TAG, "responseCaching1: "+response.networkResponse());
            }
            Response response2 = call2.execute();
            response2.body().close();
            if (response2.isSuccessful()){
                Log.d(TAG, "responseCaching cache: "+response2.cacheResponse());
                Log.d(TAG, "responseCaching2: "+response2.networkResponse());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
