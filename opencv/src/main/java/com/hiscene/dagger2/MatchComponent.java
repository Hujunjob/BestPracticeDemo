package com.hiscene.dagger2;

import com.hiscene.opencv.MainActivity;

import dagger.Component;

/**
 * Created by hujun on 2019/4/2.
 */

@Component(modules = DaggerModule.class)
public interface MatchComponent {
    void mainInject(MainActivity mainActivity);
}
