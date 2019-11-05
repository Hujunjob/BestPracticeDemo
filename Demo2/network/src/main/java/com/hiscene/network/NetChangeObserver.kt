package com.hiscene.network

/**
 * Created by junhu on 2019-11-04
 */
interface NetChangeObserver {
    fun onConnect(netType: NetType)

    fun onDisConnect()
}