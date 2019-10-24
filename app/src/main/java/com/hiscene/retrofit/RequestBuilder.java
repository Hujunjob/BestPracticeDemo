package com.hiscene.retrofit;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by junhu on 2019-10-23
 * 最终的请求拼装类
 *
 */
class RequestBuilder {
    private final String httpMethod;
    private final HttpUrl baseUrl;
    private final String reletiveUrl;
    private final boolean hasBody;

    public RequestBuilder(String httpMethod, HttpUrl baseUrl, String reletiveUrl, boolean hasBody) {
        this.httpMethod = httpMethod;
        this.baseUrl = baseUrl;
        this.reletiveUrl = reletiveUrl;
        this.hasBody = hasBody;
    }

    public void addQueryParam(String name, String value) {

    }

    public void addFieldParam(String name, String value) {

    }

    public Request build() {
        return new Request.Builder().build();
    }
}
