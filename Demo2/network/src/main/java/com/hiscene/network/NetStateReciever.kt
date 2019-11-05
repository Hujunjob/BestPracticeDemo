package com.hiscene.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by junhu on 2019-11-04
 */
class NetStateReciever : BroadcastReceiver {
    lateinit var netType: NetType
    lateinit var netChangeObserver: NetChangeObserver

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1 == null || p1.action == null) {
            return
        }


        if (p1.action.equals("")){
            if (NetworkUtils.isNetworkAvailable()) {

            }

        }
    }

    constructor() {
        netType = NetType.NONE
    }
}