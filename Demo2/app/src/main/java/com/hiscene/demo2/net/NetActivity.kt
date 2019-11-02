package com.hiscene.demo2.net

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hiscene.demo2.R
import kotlinx.android.synthetic.main.activity_net.*

class NetActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net)

        btn1.setOnClickListener { btn1Click() }
        btn2.setOnClickListener { btn2Click() }
        btn3.setOnClickListener { btn3Click() }


    }

    @NetworkCheck
    private fun btn3Click() {
        toast("btn3Click")
        var net = NetworkUtils.isNetworkAvailable(this)
        if (net){

        }
    }

    @NetworkCheck
    private fun btn2Click() {
        toast("btn2Click")
    }

    @NetworkCheck
    private fun btn1Click(){
        toast("btn1Click")
    }

    private fun toast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}
