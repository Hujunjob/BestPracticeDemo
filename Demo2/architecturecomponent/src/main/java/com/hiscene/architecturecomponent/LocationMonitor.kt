package com.hiscene.architecturecomponent

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Created by junhu on 2019-11-04
 */
class LocationMonitor:LifecycleObserver {
    var lifecycle: Lifecycle
    constructor(lifecycle:Lifecycle){
        this.lifecycle = lifecycle
        this.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(){
        println("onstart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(){
        println("onStop")
    }
}