package com.hiscene.retrofit;

import androidx.annotation.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.internal.platform.Platform;

/**
 * Created by junhu on 2019-10-25
 */
public class ProxyStudy {
    private static final String TAG = "ProxyStudyTAG";

    public interface Host {
        void hello();
    }

    public void test() {
        createProxy(MyHost.class);
//        createProxy(YourHost.class);
    }

    public class MyHost implements Host {

        @Override
        public void hello() {
            Log.d(TAG, "hello: " + getClass().getSimpleName());
        }
    }


    public class YourHost implements Host {

        @Override
        public void hello() {
            Log.d(TAG, "hello: " + getClass().getSimpleName());
        }
    }

    public <T extends Host> void createProxy(Class<T> clz) {
        T host = (T) Proxy.newProxyInstance(clz.getClassLoader(), clz.getInterfaces(), new MyInvocationHandler());
        host.hello();
    }


    private class MyInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.d(TAG, "invoke: 代理前");
//            method.invoke(proxy, args);
            Log.d(TAG, "invoke: 代理后");
            return null;
        }
    }

    static class Log {
        public static void d(String TAG, String msg) {
            System.out.println(TAG + ":" + msg);
        }
    }
}
