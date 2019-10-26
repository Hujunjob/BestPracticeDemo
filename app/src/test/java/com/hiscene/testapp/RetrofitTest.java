package com.hiscene.testapp;

import com.hiscene.retrofit.LoginService;
import com.hiscene.retrofit.Retrofit;

import org.junit.Test;

/**
 * Created by junhu on 2019-10-25
 */
public class RetrofitTest {
    @Test
    public void test(){
        Retrofit.Builder builder = new Retrofit.Builder();
        String url = "";
        builder.baseUrl(url);
        Retrofit retrofit = builder.build();

        LoginService loginService = retrofit.create(LoginService.class);
        loginService.login("胡军");
    }
}
