package com.example.facialattandance.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.facialattandance.R
import kotlinx.android.synthetic.main.activity_register_or_login.*

class RegisterOrLoginActivity : AppCompatActivity() {
    private fun init(){
        goLoginButton.setOnClickListener( View.OnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        })

        goRegisterButton.setOnClickListener( View.OnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        })




    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_or_login)
        init()
    }



}