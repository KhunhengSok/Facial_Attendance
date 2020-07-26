package com.example.facialattandance.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.facialattandance.Activity.SplashScreenActivity.Companion.saveUser
import com.example.facialattandance.R
import com.example.facialattandance.utils.HOSTING_URL
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

    private fun init() {
        requestQueue = Volley.newRequestQueue(this)
        loginButton.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "init: Log in button clicked")
            val intent = Intent(applicationContext, HomeActivity::class.java)

            var username = usernameEdit.text.toString()
            var password = passwordEdit.text.toString()

            if( !username.isBlank() && !password.isBlank() && !isLoading){
                showLoading(true)
                val requestBody = JSONObject()
                requestBody.put("username", username)
                requestBody.put("password", password)

                val request = JsonObjectRequest(Request.Method.POST, LOGIN_URL, requestBody, Response.Listener {
                    response ->
                        run {
                            try {
                                Log.d(TAG, "init: response")

                                saveUser(response)

                                val jsonObject = response
                                val token = jsonObject.getString("token")
                                val username = jsonObject.getString("username")
                                //                            Toast.makeText(this, token, Toast.LENGTH_SHORT).show()
                                if (username == usernameEdit.text.toString()) {
                                    finish()
                                    startActivity(intent)
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                                Toast.makeText(this, "Login Error!$e", Toast.LENGTH_SHORT).show()
                            }
                            showLoading(false)

                        }
                }, Response.ErrorListener {error ->
                    Toast.makeText(this, "Login Error!$error", Toast.LENGTH_SHORT).show()
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


}