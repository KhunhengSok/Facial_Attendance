package com.example.facialattandance.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.facialattandance.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private fun init() {
        registerButton.setOnClickListener(View.OnClickListener {
            //ToDo
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }
}