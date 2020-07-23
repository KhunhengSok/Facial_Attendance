package com.example.facialattandance.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.facialattandance.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()

    }

    private fun init() {
        loginButton.setOnClickListener(View.OnClickListener {
            //ToDo
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        })
    }
}