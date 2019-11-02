package com.hiscene.demo3.eventbus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by hujun on 2019-10-29.
 */

public class EventBus {
    private static volatile EventBus instance;

    private Map<Object, List<SubscribeMethod>> cacheMap;

    private Handler mHandler;

    private EventBus() {
        cacheMap = new HashMap<>();
        mHandler = new Handler();
    }

    public static synchronized EventBus getDefault() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    /**
     * 注册这个object下的所有@Subscribe的方法
     *
     * @param obj
     */
    public void register(Object obj) throws Exception {
        List<SubscribeMethod> list = cacheMap.get(obj);
        if (list == null) {
            list = findSubscribe(obj);
            cacheMap.put(obj, list);
        }
    }

    /**
     * 删除注册的方法
     *
     * @param obj
     */
    public void unRegister(Object obj) {

    }

    private List<SubscribeMethod> findSubscribe(Object obj) throws Exception {
        List<SubscribeMethod> subscribeMethods = new ArrayList<>();
        Class clz = obj.getClass();
        while (clz != null) {
            //系统级别的类，直接跳过
            String name = clz.getName();
            if (name.startsWith("java.") || name.startsWith("javax")
                    || name.startsWith("android.")) {
                break;
            }
            Method[] methods = clz.getDeclaredMethods();
            for (Method method : methods) {
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe != null) {
                    ThreadMode threadMode = subscribe.threadMode();
                    Class<?>[] classes = method.getParameterTypes();
                    if (classes.length != 1) {
                        throw new Exception("只支持一个参数传递：" + method.getName());
                    }
                    SubscribeMethod subscribeMethod = new SubscribeMethod(method, threadMode, classes[0]);
                    subscribeMethods.add(subscribeMethod);
                }
            }
            clz = clz.getSuperclass();
        }

        return subscribeMethods;
    }

    public void post(Object bean) {
        //在cacheMap里找到method
        for (Map.Entry<Object, List<SubscribeMethod>> set : cacheMap.entrySet()) {
            List<SubscribeMethod> methods = set.getValue();
            Object obj = set.getKey();
            for (SubscribeMethod method : methods) {
                if (method.getType().isAssignableFrom(bean.getClass())) {
                    switch (method.getThreadMode()) {
                        case MAIN:
                            invokeInMain(method, bean, obj);
                            break;
                        case BACKGROUND:
                            invokeInBack(method, bean, obj);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

    }

    private void invokeInBack(SubscribeMethod method, Object bean, Object obj) {
        //主线程调用
        if (Looper.myLooper() == Looper.getMainLooper()) {
//            ExecutorService ThreadPoolExecutor ScheduledExecutorService ScheduledThreadPoolExecutor
//            Executors.newFixedThreadPool()
//            new ThreadPoolExecutor()
            ExecutorService service = Executors.newFixedThreadPool(10);
            service.execute(new Runnable() {
                @Override
                public void run() {

                }
            });
            Future<?> future = service.submit(new Runnable() {
                @Override
                public void run() {

                }
            });
            Future<String> future1 = service.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return null;
                }
            });

        } else {
            //子线程调用
            try {
                method.getMethod().invoke(obj, bean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void invokeInMain(final SubscribeMethod method, final Object bean, final Object obj) {
        //就是在主线程调用的
        if (Looper.myLooper() == Looper.getMainLooper()) {
            try {
                method.getMethod().invoke(obj, bean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            //在子线程调用的
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        method.getMethod().invoke(obj, bean);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
