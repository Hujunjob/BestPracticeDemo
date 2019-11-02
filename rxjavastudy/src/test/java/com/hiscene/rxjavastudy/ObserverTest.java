package com.hiscene.rxjavastudy;

import com.hiscene.rxjavastudy.observable.Observable;
import com.hiscene.rxjavastudy.observable.ObservableImp;
import com.hiscene.rxjavastudy.observable.ObserverImp;

import org.junit.Test;

/**
 * Created by junhu on 2019-10-24
 */
public class ObserverTest {
    @Test
    public void test(){
        //一个被观测者
        ObservableImp observableImp = new ObservableImp();
        //多个观测者
        ObserverImp observerImp1 = new ObserverImp();
        ObserverImp observerImp2 = new ObserverImp();
        ObserverImp observerImp3 = new ObserverImp();

        observableImp.registerObserver(observerImp1);
        observableImp.registerObserver(observerImp3);
        observableImp.registerObserver(observerImp2);

        observableImp.change("偷东西1");

        ObserverImp observerImp4 = new ObserverImp();
        observableImp.registerObserver(observerImp4);
        observableImp.change("偷东西2");

        observableImp.removeObserver(observerImp1);
        observableImp.change("偷东西3");
    }
}
