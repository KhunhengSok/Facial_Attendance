package com.example.facialattandance.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.facialattandance.Activity.SplashScreenActivity.Companion.saveOrganization
import com.example.facialattandance.Activity.SplashScreenActivity.Companion.saveUser
import com.example.facialattandance.Model.Department
import com.example.facialattandance.R
import com.example.facialattandance.utils.HOSTING_URL
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {
    companion object{
        val TAG = "LoginActivity"
    }

    private val LOGIN_URL = HOSTING_URL + "api/account/login"
    val PERMISSION_FINISH = false
    var requestQueue:RequestQueue ?= null
    private var isLoading  = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        showLoading(false)
        init()
    }

    @SuppressLint("ResourceAsColor")
    private fun init() {
        requestQueue = Volley.newRequestQueue(this)
        window.statusBarColor = R.color.white
        
        loginButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "init: Log in button clicked")

            var username = usernameEdit.text.toString()
            var password = passwordEdit.text.toString()

            if( !username.isBlank() && !password.isBlank() && !isLoading){
                showLoading(true)
                val requestBody = JSONObject()
                requestBody.put("username", username)
                requestBody.put("password", password)

                Log.d(TAG, "init: $LOGIN_URL")
                val request = JsonObjectRequest(Request.Method.POST, LOGIN_URL, requestBody, Response.Listener {
                    response ->
                        run {
                            try {
                                Log.d(TAG, "init: response")

                                saveUser(response)
                                Log.d(TAG, "init: id is ${response.getInt("id")}")
                                loadDepartment(response.getInt("id"))
                                /*val username = response.getString("username")

                                if (username == usernameEdit.text.toString()) {
                                    finish()
                                    startActivity(intent)
                                }*/
                            } catch (e: JSONException) {
                                e.printStackTrace()
                                Toast.makeText(this, "Login Error!$e", Toast.LENGTH_SHORT).show()
                            }
                            showLoading(false)

                        }
                }, Response.ErrorListener {error ->
                    Toast.makeText(this, "Username and password not matched.", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "init: error ${error}")
                    showLoading(false)

                } )
                requestQueue!!.add(request)
            }else if (username.isBlank() || password.isBlank()){
                Log.d(TAG, "init: Blank ")
                Toast.makeText(baseContext, "Please fill the username and password properly.", Toast.LENGTH_SHORT).show()
            }

        })
    }


    fun showLoading(loading:Boolean){
        isLoading = loading
        Log.d(TAG, "showLoading: ${loading}")
        if(isLoading){
            loginProgressBar.visibility = ProgressBar.VISIBLE
            blurBackground.visibility = LinearLayout.VISIBLE
            usernameEdit?.isEnabled = false
            passwordEdit?.isEnabled = false
        }else{
            loginProgressBar.visibility = ProgressBar.INVISIBLE
            blurBackground.visibility = LinearLayout.INVISIBLE
            usernameEdit?.isEnabled = true
            passwordEdit?.isEnabled = true
        }

    }


    fun loadDepartment(userId:Int){
        val Get_Department_Url = HOSTING_URL + "api/account/$userId/organization"

        Log.d(SplashScreenActivity.TAG, "getDepartment")
        val gson = Gson()
        Log.d(SplashScreenActivity.TAG, "getDepartment: url: $Get_Department_Url")
        val request = object: JsonArrayRequest(Request.Method.GET, Get_Department_Url, null, Response.Listener {
            response ->
                run {
                    Log.d(SplashScreenActivity.TAG, "getDepartment: response")
                    val departments = gson.fromJson(response.toString(), Array<Department>::class.java)
                    SplashScreenActivity.currentOrganization = departments[0]
                    saveOrganization(departments[0])

                    Log.d(TAG, "getDepartment: assign department")

                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
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

}