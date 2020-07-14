package com.example.facialattandance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.facialattandance.fragment.CameraFragment
import com.example.facialattandance.fragment.setupFragment
import kotlinx.android.synthetic.main.activity_tab.*

class TabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        setSupportActionBar(toolbar)

        // Show ProductsFragment as default
        showFragment(CameraFragment())

        // Handle event when user clicks on Bottom Nav items
        bottomNav.setOnNavigationItemSelectedListener {

            when (it.itemId) {
//                R.id.mnu_camera -> {
//                    supportActionBar!!.setTitle(R.string.camera)
//                }
//                R.id.mnu_categories -> {
//                    supportActionBar!!.setTitle(R.string.categories)
//                    showFragment(CategoriesFragment())
//                }
//                R.id.mnu_profile -> {
//                    supportActionBar!!.setTitle(R.string.profile)
//                    showFragment(ProfileFragment())
//                }
//                else -> {
//                    supportActionBar!!.setTitle(R.string.settings)
//                    showFragment(SettingsFragment())
//                }
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
