package com.example.facialattandance.Activity

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.facialattandance.Model.Employee
import com.example.facialattandance.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_camera.*


class ProfileActivity : AppCompatActivity() {

    object EXTRA_EMPLOYEE {
        val CODE = "EMPLOYEE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

//        var image = findViewById(R.id.image_profile)
        val intent = intent
        val employeeJson = intent.getStringExtra(EXTRA_EMPLOYEE.CODE)
        val gson = Gson()
        val employee = gson.fromJson<Employee>(employeeJson, Employee::class.java)
        Toast.makeText(this@ProfileActivity!!,employee.imageUrl,Toast.LENGTH_SHORT).show()
//
        name.setText(employee.name)
        position.setText(employee.postion)
        department.setText(employee.department)
        image_profile.setImageURI(employee.imageUrl)


    }
}
