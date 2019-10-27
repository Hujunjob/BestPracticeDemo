package com.hiscene.retrofit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Created by junhu on 2019-10-23
 */
public class Retrofit {
    //为了避免重复代理同一个方法，需要缓存起来
    private final Map<Method, ServiceMethod> serviceMethodCache = new HashMap<>();
    //请求地址
    private HttpUrl baseUrl;

    //还需要发起OKHttp请求
    private Call.Factory callFactory;

    private Retrofit(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.callFactory = builder.callFactory;
    }

    public HttpUrl getBaseUrl() {
        return baseUrl;
    }

    public Call.Factory getCallFactory() {
        return callFactory;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> clz) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{clz}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //可以拦截方法名，方法注解值，方法参数注解，参数名，方法参数值
                        //将这些信息封装到ServiceMethod中
                        ServiceMethod serviceMethod = loadServiceMethod(method);
                        //创建okhttp的请求对象
                        Call call = serviceMethod.toCall(args);
                        return call;
                    }
                });
    }

    private ServiceMethod loadServiceMethod(Method method) {
        ServiceMethod result = serviceMethodCache.get(method);
        if (result != null) {
            return result;
        }

        //同步锁
        synchronized (serviceMethodCache) {
            //同方法请求，刚进来时，1、2都是null
            result = serviceMethodCache.get(method);
            if (result == null) {
                result = new ServiceMethod.Builder(this, method).build();
                serviceMethodCache.put(method,result);
            }
        }


        return result;
    }

    public static class Builder {
        private Retrofit retrofit;

        //请求地址
        private HttpUrl baseUrl;

        //还需要发起OKHttp请求
        private Call.Factory callFactory;

        public HttpUrl getBaseUrl() {
            return baseUrl;
        }

        public Builder setBaseUrl(HttpUrl baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setBaseUrl(String url) {
            if (url.isEmpty()) {
                throw new NullPointerException("base url is null!");
            }
            setBaseUrl(HttpUrl.parse(url));
            return this;
        }

        public Builder setCallFactory(Call.Factory callFactory) {
            this.callFactory = callFactory;
            return this;
        }

        //构建者模式，可以做参数的校验和初始化
        public Retrofit build() {
            if (this.callFactory == null) {
                this.callFactory = new OkHttpClient();
            }
            if (baseUrl == null) {
                throw new NullPointerException("Baseurl is null!");
            }
            retrofit = new Retrofit(this);
            return retrofit;
        }
    }
}
