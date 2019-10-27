package com.hiscene.dagger2;

import javax.inject.Inject;

/**
 * Created by hujun on 2019/4/2.
 */

public class D {
    @Inject
    public D(){}

    public void hello(){
        System.out.print("D hello");
    }
}
