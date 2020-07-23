package com.example.facialattandance.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.facialattandance.R
import com.example.facialattandance.fragment.CameraFragment
import com.example.facialattandance.fragment.HomeFragment
import com.example.facialattandance.fragment.SetupFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)

        // Show ProductsFragment as default
        showFragment(HomeFragment())

        // Handle event when user clicks on Bottom Nav items
        bottomNav.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.mnu_camera -> {
                    supportActionBar!!.setTitle(R.string.camera)
                    showFragment(CameraFragment())
                }
                R.id.mnu_home -> {
                    supportActionBar!!.setTitle(R.string.home)
                    showFragment(HomeFragment())
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
