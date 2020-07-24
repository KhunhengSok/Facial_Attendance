package com.example.facialattandance.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.facialattandance.R
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class LoginActivity : AppCompatActivity() {
    private val LOGIN_URL = "https://face-attendance-api.herokuapp.com/api/account/login"
    val PERMISSION_FINISH = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()

    }

    private fun init() {
        loginButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, HomeActivity::class.java)
//            finish()

//            val request: RequestQueue = Volley.newRequestQueue(this)
            var username = usernameEdit.text.toString()
            var password = passwordEdit.text.toString()
//
//            //String Request initialized
//            var mStringRequest = object : StringRequest(Request.Method.POST, LOGIN_URL, Response.Listener { response ->
//                Toast.makeText(this, "Successfully" + response, Toast.LENGTH_SHORT).show()
//            }, Response.ErrorListener { error ->
//                Log.i("This is the error", "Error :" + error.toString())
//            Toast.makeText(this, "Please make sure you enter correct password and username", Toast.LENGTH_SHORT).show()
//            }) {
//                override fun getBodyContentType(): String {
//                    return "application/json"
//                }
//
//                override fun getParams(): MutableMap<String, String> {
//                    var param = HashMap<String,String>()
//                    param.put("username", username)
//                    param.put("password", password)
//                    return param
//                }
//            }
//            request!!.add(mStringRequest!!)


            val requestQueue = Volley.newRequestQueue(this)


            val stringRequest: StringRequest = object : StringRequest(Method.POST, LOGIN_URL,
                    Response.Listener { response ->
                        try {
                            val jsonObject = JSONObject(response)
                            val token = jsonObject.getString("token")
                            val username = jsonObject.getString("username")
                            Toast.makeText(this, token, Toast.LENGTH_SHORT).show()
                            if (username == usernameEdit.text.toString()) {
                                startActivity(intent)
                                finish()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            Toast.makeText(this, "Register Error!$e", Toast.LENGTH_SHORT).show()
                        }
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(this, "Register Error!$error", Toast.LENGTH_SHORT).show()
                    }) {


                override fun getParams(): MutableMap<String, String> {
                    var param = HashMap<String,String>()
                    param.put("username", username)
                    param.put("password", password)
                    return param
                }
            }

            requestQueue.add(stringRequest)



        })
    }
}