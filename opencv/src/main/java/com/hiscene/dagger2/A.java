package com.hiscene.dagger2;

import javax.inject.Inject;

/**
 * Created by hujun on 2019/4/2.
 */

public class A {
    @Inject
    B b;

    @Inject
    C c;

    @Inject
    public A(){}

    public void birth(){
        System.out.print("A call B birth");
        b.birth();
    }

    public void hello(){
        System.out.print("A say hello to C");
        c.hello();
    }

}
