package com.example.facialattandance.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.facialattandance.R
import com.example.facialattandance.fragment.*
import com.example.facialattandance.utils.capitalize
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)



        // Show ProductsFragment as default
        showFragment(MeetingFragment())

        // Handle event when user clicks on Bottom Nav items
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                /*R.id.mnu_camera -> {
                    supportActionBar!!.setTitle(R.string.camera)
                    showFragment(CameraFragment())
                }*/
                R.id.mnu_home -> {
//                    supportActionBar!!.setTitle(R.string.meeting)
                    supportActionBar!!.setTitle(capitalize(SplashScreenActivity.currentOrganization?.name!!))
                    showFragment(MeetingFragment())
                }
                R.id.mnu_employee -> {
                    supportActionBar!!.setTitle(R.string.employee)
//                    showFragment(AddEmployeeFragment())
                    showFragment(EmployeeFragment())
                }
                /*  R.id.mnu_setup -> {
                      supportActionBar!!.setTitle(R.string.setup)
                      showFragment(SetupFragment())
                  }*/
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_homeactivity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                SplashScreenActivity.removeCurrentUser()
                SplashScreenActivity.removeCurrentOrganization()
                startActivity(Intent(baseContext, LoginActivity::class.java))
            }

        }
        return true
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.lytFragmentContainer, fragment)
        fragmentTransaction.commit()
    }


}