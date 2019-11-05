package com.hiscene.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.math.log

class MainActivity : AppCompatActivity(),NetChangeObserver{
    override fun onConnect(netType: NetType) {

    }

    override fun onDisConnect() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


}
