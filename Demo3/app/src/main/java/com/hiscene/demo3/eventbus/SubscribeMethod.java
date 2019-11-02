package com.hiscene.demo3.eventbus;

import java.lang.reflect.Method;

/**
 * Created by hujun on 2019-10-31.
 */

public class SubscribeMethod {
    //方法本身
    private Method method;

    private ThreadMode threadMode;

    //方法中的参数
    private Class<?> type;

    public SubscribeMethod(Method method, ThreadMode threadMode, Class<?> type) {
        this.method = method;
        this.threadMode = threadMode;
        this.type = type;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
