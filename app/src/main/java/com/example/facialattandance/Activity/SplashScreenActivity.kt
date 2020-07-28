package com.example.facialattandance.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.facialattandance.Model.Department
import com.example.facialattandance.Model.User
import com.example.facialattandance.R
import com.example.facialattandance.utils.*
import com.google.gson.Gson
import org.json.JSONObject


class SplashScreenActivity : AppCompatActivity() {
    private var requestQueue:RequestQueue ?= null
    var Get_Organization_Url:String ?=null

    fun init(){
//        val prefs = getSharedPreferences("isStarted", Context.MODE_PRIVATE)
//        val isStarted = prefs.getBoolean("isStarted", false)
        val isStarted = true

        userSharedPreference = getSharedPreferences(USER_SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)
        requestQueue = Volley.newRequestQueue(this)
        currentUser = retrieveUser()
        currentOrganization = retrieveOrganization()

        Handler().postDelayed({
            if(isStarted) {
                if(currentUser != null){
                    Log.d(TAG, "init: current user is not null")
                    Get_Organization_Url = HOSTING_URL + "api/account/${currentUser!!.id}/organization"
                    loadOrganization(currentUser!!.id)

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

    fun loadOrganization(userId:Int){
        Log.d(TAG, "getDepartment")
        val gson = Gson()
        Log.d(TAG, "getDepartment: url: $Get_Organization_Url")
        val request = object:JsonObjectRequest(Request.Method.GET, Get_Organization_Url, null, Response.Listener {
            response ->
            run {
                Log.d(TAG, "getDepartment: response")
                val department = gson.fromJson(response.toString(), Department::class.java)
                currentOrganization = department
                
                Log.d(TAG, "getDepartment: assign department")
            }
        }, Response.ErrorListener {
            error ->
            run {
                Log.d(TAG, "getDepartment: ${error.message}")
            }
        }){
            override fun getHeaders(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                if(SplashScreenActivity.currentUser == null){
                    SplashScreenActivity.currentUser = SplashScreenActivity.retrieveUser()
                }
                Log.d(EmployeeActivity.TAG, "getHeaders: token: ${SplashScreenActivity.currentUser!!.token}")
                params.put("Content-Type", "application/json")
                params.put("Authorization", "Bearer " + SplashScreenActivity.currentUser!!.token)
                return params
            }
        }
        requestQueue?.add(request)


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slash_screen)
        init()
    }

    companion object{
        var currentUser:User ?= null
        var currentOrganization: Department?=null
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



        /*fun saveDepartment(department:JSONObject){
            val gson  = Gson()
            val editor = userSharedPreference!!.edit()
            Log.d(TAG, "saveDepartment: ${department.toString()}")
            editor.putString(DEPARTMENT_SHARED_PREFERENCE_KEY, department.toString())
            if(editor.commit()){
                Log.d(TAG, "saveDepartment: Saved department info to sharedpreferences.")
            }else{
                Log.d(TAG, "saveDepartment: Can not save the department info to sharedpreferences")
            }
        }*/

        fun saveOrganization(department: Department){
            val gson = Gson()
            val departmentJson = gson.toJson(department)
            val editor = userSharedPreference!!.edit()
            Log.d(TAG, "saveDepartment: ${departmentJson.toString()}")
            editor.putString(DEPARTMENT_SHARED_PREFERENCE_KEY, departmentJson.toString())
            if(editor.commit()){
                Log.d(TAG, "saveDepartment: Saved department info to sharedpreferences.")
            }else{
                Log.d(TAG, "saveDepartment: Can not save the department info to sharedpreferences")
            }
        }

        fun retrieveOrganization(): Department?{
            val gson = Gson()
            val departmentJson = userSharedPreference?.getString(DEPARTMENT_SHARED_PREFERENCE_KEY, "")
            if (departmentJson!!.isBlank()){
                Log.d(TAG, "retrieveDepartment: null")
                return null
            }else{
                Log.d(TAG, "retrieveDepartment: non-null")
                return gson.fromJson(departmentJson, Department::class.java)
            }
        }

        fun removeCurrentOrganization(){
            currentOrganization = null
            val editor = userSharedPreference!!.edit()
            editor.remove(DEPARTMENT_SHARED_PREFERENCE_KEY)
            if(editor.commit()){
                Log.d(TAG, "removeCurrentDepartment: removed")
            }else{
                Log.d(TAG, "removeCurrentDepartment: unable to removed")
            }
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

        fun removeCurrentUser(){
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


