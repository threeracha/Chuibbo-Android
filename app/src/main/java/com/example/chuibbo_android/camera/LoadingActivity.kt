package com.example.chuibbo_android.camera

import android.os.Bundle
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.chuibbo_android.R
import kotlinx.android.synthetic.main.loading_activity.*

class LoadingActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_activity)
        val logo = findViewById<ImageView>(R.id.loadingIcon)
        val animation : Animation = AnimationUtils.loadAnimation(this, R.anim.loading)
        logo.startAnimation(animation)
        println("starting animation...")
    }
}