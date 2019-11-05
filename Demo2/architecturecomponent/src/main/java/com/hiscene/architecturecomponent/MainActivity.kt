package com.hiscene.architecturecomponent

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var model: NameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        model = ViewModelProvider(this).get(NameViewModel::class.java)

        model.getUserInfo().observe(this, Observer {
            if (it.isNotEmpty())
            it[0].run {
                txt_info.text = "name=$name,id=$id,age=$age"
            }
        })

        btn_sure.setOnClickListener {
            if (TextUtils.isEmpty(edit_age.text) || TextUtils.isEmpty(edit_name.text) || TextUtils.isEmpty(edit_no.text)) {
                toast("请输入所有信息")
            } else {
                var userInfo = UserInfo(edit_no.text.toString().toInt(), edit_name.text.toString(), edit_age.text.toString().toInt())
                model.insert(userInfo)
            }
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
