package com.example.facialattandance.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.facialattandance.Model.Employee
import com.example.facialattandance.R
import com.example.facialattandance.adapter.EmployeeAdapter
import com.example.facialattandance.utils.HOSTING_URL
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_employee.*

class EmployeeActivity : AppCompatActivity() {
    private var Employee_Url: String? = null
    private var organizationId = SplashScreenActivity.currentOrganization!!.id
    private var requestQueue:RequestQueue ?=null

    fun init(){
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recycler_view!!.layoutManager = layoutManager
        Employee_Url = HOSTING_URL + "api/organization/$organizationId/employees"
        Log.d(TAG, "init: ${Employee_Url}")
        requestQueue = Volley.newRequestQueue(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)
        init()
        loadEmployee()
    }

    private fun loadEmployee() {
        // Show progress bar and hide recycler view
        showLoading(true)

        val request = object:JsonArrayRequest(Employee_Url, Response.Listener { response ->
            Log.d(TAG, "loadEmployee: Response")
            showLoading(false)

            // Deserialize json using gson library
            val gson = Gson()
            val employees = gson.fromJson(response.toString(), Array<Employee>::class.java)
            val adapter = EmployeeAdapter(employees)
            recycler_view!!.adapter = adapter
        }, Response.ErrorListener { error ->
            showLoading(false)
            Toast.makeText(this@EmployeeActivity, "Load data error.", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Load data error: " + error.message)
        }){
            override fun getHeaders(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                if(SplashScreenActivity.currentUser == null){
                    SplashScreenActivity.currentUser = SplashScreenActivity.retrieveUser()
                }
                Log.d(TAG, "getHeaders: token: ${SplashScreenActivity.currentUser!!.token}")
                params.put("Content-Type", "application/json")
                params.put("Authorization", "Bearer " + SplashScreenActivity.currentUser!!.token)
                return params
            }
        }


        // Add request to Queue
        requestQueue!!.add(request)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progress_bar!!.visibility = View.VISIBLE
            recycler_view!!.visibility = View.INVISIBLE
        } else {
            progress_bar!!.visibility = View.INVISIBLE
            recycler_view!!.visibility = View.VISIBLE
        }
    }

    companion object{
        val TAG = "EmployeeActivity"

    }
}


