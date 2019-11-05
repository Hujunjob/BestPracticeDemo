package com.hiscene.architecturecomponent

import android.app.Application

/**
 * Created by junhu on 2019-11-05
 */
class AppApplication:Application() {
    companion object{
        lateinit var instance:Application
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}