package com.hiscene.dagger2;

import javax.inject.Inject;

/**
 * Created by hujun on 2019/4/2.
 */

public class B {
    @Inject
    public B(){}

    public void birth(){
        System.out.print("B birth");
    }
}
