package com.example.facialattandance.Activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.facialattandance.Model.Employee
import com.example.facialattandance.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.department
import kotlinx.android.synthetic.main.activity_profile.name
import kotlinx.android.synthetic.main.fragment_camera.*
import kotlinx.android.synthetic.main.view_holder_employee.*


class ProfileActivity : AppCompatActivity() {

    companion  object EXTRA_EMPLOYEE {
        val TAG = "ProfileActivity"
        val CODE = "EMPLOYEE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val intent = intent
        val employeeJson = intent.getStringExtra(EXTRA_EMPLOYEE.CODE)
        val gson = Gson()
        val employee = gson.fromJson<Employee>(employeeJson, Employee::class.java)
//        Toast.makeText(this@ProfileActivity!!,employee.profile_url,Toast.LENGTH_SHORT).show()

        name.setText(employee.name)
        position.setText(employee.position)
        department.setText(employee.department)
        employ_date_textview.text = employee.employed_date
//        email_textview.text = employee.email
        birth_of_date_textview.text = employee.birth_of_date
        image_profile.setImageURI(Uri.parse(employee.profile_url))
        Log.d(TAG, "onCreate: ${employee.profile_url}")
//        Glide.with(this).load(Uri.parse(employee.profile_url)).into(image_profile)


    }
}
