package com.hiscene.rxjavastudy.observable;

/**
 * Created by junhu on 2019-10-24
 * 被观察者
 */
public interface Observable {
    //注册观察者
    void registerObserver(Observer observer);
    //解除观察
    void removeObserver(Observer observer);


    //产生事件
    void change(String msg);

}
