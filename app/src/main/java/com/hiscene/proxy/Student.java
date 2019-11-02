package com.hiscene.proxy;

/**
 * Created by junhu on 2019-10-25
 */
public class Student implements Person {
    @Override
    public void say(String msg) {
        System.out.println(getClass().getSimpleName()+":"+msg);
    }

    @Override
    public boolean sleep() {
        System.out.println(getClass().getSimpleName()+":sleep");
        return false;
    }
}
