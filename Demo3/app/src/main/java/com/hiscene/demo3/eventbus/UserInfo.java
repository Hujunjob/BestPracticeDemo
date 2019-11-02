package com.hiscene.demo3.eventbus;

import androidx.annotation.NonNull;

/**
 * Created by hujun on 2019-10-31.
 */

public class UserInfo {
    String name;
    int age;

    public UserInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @NonNull
    @Override
    public String toString() {
        return "name:"+name+",age="+age;
    }
}

