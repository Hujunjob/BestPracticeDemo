package com.hiscene.network

import android.app.Application
import android.content.IntentFilter
import androidx.lifecycle.LiveData

/**
 * Created by junhu on 2019-11-04
 */
object NetworkManager {
    var reciever:NetStateReciever
    lateinit var application: Application

    init {
        reciever = NetStateReciever()
    }

    fun init(application: Application){
        this.application = application

        val filter = IntentFilter()
        filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION)
        application.registerReceiver(reciever,filter)

    }
}