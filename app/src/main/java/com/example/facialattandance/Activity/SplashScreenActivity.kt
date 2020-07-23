package com.example.facialattandance.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.facialattandance.R

//import sun.jvm.hotspot.utilities.IntArray


class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 500 //0.5s

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slash_screen)
        val prefs = getSharedPreferences("isStarted", Context.MODE_PRIVATE)
        val isStarted = prefs.getBoolean("isStarted", false)
        Handler().postDelayed({
            if(isStarted) {
                startActivity(Intent(this, RegisterOrLoginActivity::class.java))
            } else {
                startActivity(Intent(this, WelcomeNewUserActivity::class.java))
            }
            finish()
        }, SPLASH_TIME_OUT)
    }
}
