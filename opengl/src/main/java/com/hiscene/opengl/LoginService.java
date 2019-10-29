package com.hiscene.opengl;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by hujun on 2019-10-27.
 */

public interface LoginService {
    @GET("p/5260135/")
    Call<Response<Object>> getName();
}
