package com.example.facialattandance.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.facialattandance.Model.Department
import com.example.facialattandance.Model.User
import com.example.facialattandance.R
import com.example.facialattandance.utils.USER_SHARED_PREFERENCE_KEY
import com.example.facialattandance.utils.SPLASH_TIME_OUT
import com.example.facialattandance.utils.USER_KEY
import com.google.gson.Gson
import org.json.JSONObject


class SplashScreenActivity : AppCompatActivity() {

    fun init(){
        userSharedPreference = getSharedPreferences(USER_SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)

        currentUser = retrieveUser()
        val prefs = getSharedPreferences("isStarted", Context.MODE_PRIVATE)
        val isStarted = prefs.getBoolean("isStarted", false)

        Handler().postDelayed({
            if(isStarted) {
                if(currentUser != null){
                    Log.d(TAG, "init: current user is not null")
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }else{
                    Log.d(TAG, "init: current user is null")
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            } else {
                startActivity(Intent(this, WelcomeNewUserActivity::class.java))
            }
            finish()
        }, SPLASH_TIME_OUT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slash_screen)
        init()
    }

    companion object{
        var currentUser:User ?= null
        var currentDepartment: Department?=null
        val TAG = "SplashScreenActivity"
        var userSharedPreference:SharedPreferences ?= null

        fun saveUser(user: User){
            val gson = Gson()
            val editor = userSharedPreference!!.edit()
            val json = gson.toJson(user)
            Log.d(TAG, "saveUser: ${json.toString()}")
            editor.putString(USER_KEY, json.toString())
            if ( editor.commit()){
                Log.d(TAG, "saveUser: Save user to sharedpreferences")
            }else{
                Log.d(TAG, "saveUser: Can not save user to sharedpreferences")
            }

        }

        fun saveUser(userJson: JSONObject){
            val gson = Gson()
            val user = gson.fromJson(userJson.toString(), User::class.java)
            saveUser(user)
        }


        fun retrieveUser(): User? {
            val gson = Gson()

            val userJson = userSharedPreference!!.getString(USER_KEY, "")
            Log.d(TAG, "retrieveUser: ")
            Log.d(TAG, "retrieveUser: $userJson")

            if( userJson!!.isBlank()){
                Log.d(TAG, "retrieveUser: null")
                return null
            }else{
                val user = gson.fromJson(userJson, User::class.java)
                Log.d(TAG, "retrieveUser: not-null")
                return user
            }
        }

        fun deleteCurrentUser(){
            currentUser = null
            val editor = userSharedPreference!!.edit()
            editor.remove(USER_KEY)
            if(editor.commit()){
                Log.d(TAG, "deleteCurrentUser: Log out ")
            }else{
                Log.d(TAG, "deleteCurrentUser: Cannot log out")
            }
        }
    }
}
