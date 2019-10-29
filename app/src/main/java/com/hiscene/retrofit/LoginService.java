package com.hiscene.retrofit;

import okhttp3.Response;

/**
 * Created by junhu on 2019-10-23
 */
public interface LoginService {
    @GET("/login")
    OKHttpCall<Response> login(@Query("name") String name);

    @POST("/logout")
    OKHttpCall<Response> logout();
}
