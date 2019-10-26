package com.hiscene.rxjavastudy.observable;

/**
 * Created by junhu on 2019-10-24
 * 观察者
 */
public interface Observer {
    //接收信息
    void change(Object object);
}
