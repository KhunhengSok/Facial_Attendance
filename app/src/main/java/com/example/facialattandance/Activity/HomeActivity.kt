package com.example.facialattandance.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.facialattandance.R
import com.example.facialattandance.fragment.AddEmployeeFragment
import com.example.facialattandance.fragment.CameraFragment
import com.example.facialattandance.fragment.MeetingFragment
import com.example.facialattandance.fragment.SetupFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)

//        logoutButton.setOnClickListener(
//            View.OnClickListener {
//                view ->
//                    run {
//                        SplashScreenActivity.deleteCurrentUser()
//                        startActivity(Intent(baseContext, LoginActivity::class.java))
//                    }
//            }
//        )

        // Show ProductsFragment as default
        showFragment(MeetingFragment())

        // Handle event when user clicks on Bottom Nav items
        bottomNav.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.mnu_camera -> {
                    supportActionBar!!.setTitle(R.string.camera)
                    showFragment(CameraFragment())
                }
                R.id.mnu_home -> {
                    supportActionBar!!.setTitle(R.string.meeting)
                    showFragment(MeetingFragment())
                }
                R.id.mnu_attendee -> {
                    supportActionBar!!.setTitle(R.string.attendee)
                    showFragment(AddEmployeeFragment())
                }
                R.id.mnu_setup -> {
                    supportActionBar!!.setTitle(R.string.setup)
                    showFragment(SetupFragment())
                }
            }

            true
        }
    }

    private fun showFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.lytFragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}
