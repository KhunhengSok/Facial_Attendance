package com.example.facialattandance.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.facialattandance.Activity.EmployeeActivity
import com.example.facialattandance.Activity.SplashScreenActivity
import com.example.facialattandance.Model.Employee
import com.example.facialattandance.R
import com.example.facialattandance.adapter.EmployeeAdapter
import com.example.facialattandance.utils.HOSTING_URL
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_absent_attendees.*

class AbsentAttendeesFragment : Fragment() {

    var requestQueue: RequestQueue? = null
    var AbsentUrl = ""


    fun init() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        recycler_view!!.layoutManager = layoutManager
        Log.d(TAG, "init: ${AbsentUrl}")
        requestQueue = Volley.newRequestQueue(context)


    }
    override fun onResume() {
        super.onResume()
        loadEmployee()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_absent_attendees, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.get("id")

        AbsentUrl = HOSTING_URL + "api/event/$id/absent"
        init()
        loadEmployee()
    }

    private fun loadEmployee() {
        // Show progress bar and hide recycler view
        showLoading(true)

        val request = object : JsonArrayRequest(AbsentUrl, Response.Listener { response ->
            Log.d(TAG, "loadEmployee: Response")
            showLoading(false)


            // Deserialize json using gson library
            val gson = Gson()
            val employees = gson.fromJson(response.toString(), Array<Employee>::class.java)
//            Log.d(TAG, "loadEmployee: ${employees[0].name}")
            if (employees.isNotEmpty()) {
                val adapter = EmployeeAdapter(employees)
                recycler_view?.adapter = adapter
                Log.d(TAG, "loadEmployee: set adapter")
            } else {
                none_indicator.visibility = View.VISIBLE
                none_indicator.text = "None"
            }
        }, Response.ErrorListener { error ->
            showLoading(false)
            Toast.makeText(activity, "Load data error.", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Load data error: " + error.message)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                if (SplashScreenActivity.currentUser == null) {
                    SplashScreenActivity.currentUser = SplashScreenActivity.retrieveUser()
                }
                Log.d(TAG, "getHeaders: token: ${SplashScreenActivity.currentUser!!.token}")
                params.put("Content-Type", "application/json")
                params.put("Authorization", "Bearer " + SplashScreenActivity.currentUser!!.token)
                return params
            }
        }

        requestQueue?.add(request)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progress_bar?.visibility = View.VISIBLE
            recycler_view?.visibility = View.INVISIBLE
        } else {
            progress_bar?.visibility = View.INVISIBLE
            recycler_view?.visibility = View.VISIBLE
        }
    }

    companion object {
        val TAG = "AbsentAttendeesFragment"

    }
}