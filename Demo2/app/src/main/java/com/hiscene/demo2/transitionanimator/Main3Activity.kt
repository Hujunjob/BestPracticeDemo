package com.hiscene.demo2.transitionanimator

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.Window
import com.hiscene.demo2.R
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {
    lateinit var transition: Transition

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        transition = TransitionInflater.from(this).inflateTransition(R.transition.explode)
        window.exitTransition = transition
        window.enterTransition = transition
        window.reenterTransition = transition
        setContentView(R.layout.activity_main3)

        btn_go3.setOnClickListener{
            var intent = Intent(this,Main4Activity::class.java)
            startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this,img_lyf,img_lyf.transitionName).toBundle())
        }
    }
}
