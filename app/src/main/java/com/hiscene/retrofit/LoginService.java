package com.hiscene.retrofit;

/**
 * Created by junhu on 2019-10-23
 */
public interface LoginService {
    @GET("/login")
    void login(@Query("name") String name);

    @POST("/logout")
    void logout();
}
