package com.hiscene.rxjavastudy.observable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junhu on 2019-10-24
 */
public class ObservableImp implements Observable {
    private List<Observer> observables = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer) {
        if (observer != null) {
            observables.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observer != null) {
            observables.remove(observer);
        }
    }

    @Override
    public void change(String msg) {
        for (Observer observable : observables) {
            observable.change(msg);
        }
    }
}
