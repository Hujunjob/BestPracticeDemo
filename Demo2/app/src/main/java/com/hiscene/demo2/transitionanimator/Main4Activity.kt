package com.hiscene.demo2.transitionanimator

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.view.Window
import com.hiscene.demo2.R
import kotlinx.android.synthetic.main.activity_main4.*


class Main4Activity : AppCompatActivity() {
    lateinit var transition: Transition
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        transition = TransitionInflater.from(this).inflateTransition(R.transition.fade)
        window.exitTransition = transition
        window.enterTransition = transition
        window.reenterTransition = transition
        setContentView(R.layout.activity_main4)

        btn_go4.setOnClickListener {
            var intent = Intent(this, Main3Activity::class.java)
            var pair = android.util.Pair<View,String>(img_wlh,img_wlh.transitionName)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, pair).toBundle())
        }
    }
}
