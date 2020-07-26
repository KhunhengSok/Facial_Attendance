package com.example.facialattandance.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.facialattandance.Activity.EmployeeActivity
import com.example.facialattandance.Activity.SplashScreenActivity
import com.example.facialattandance.Model.Meeting
import com.example.facialattandance.R
import com.example.facialattandance.adapter.MeetingAdapter
import com.example.facialattandance.utils.HOSTING_URL
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_meeting.*


class MeetingFragment : Fragment() {
    private var organizationId = 1
    private var Meeting_Url = HOSTING_URL + "api/organization/$organizationId/events"
    private var requestQueue:RequestQueue ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_meeting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(false)
        requestQueue =  Volley.newRequestQueue(context)

        loadMeeting()
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recycler_view_meeting.layoutManager = layoutManager
    }

    private fun loadMeeting() {
        // Show progress bar and hide recycler view
        showLoading(true)

        // Initialize request
        val request = object:JsonArrayRequest(Meeting_Url, Response.Listener { response ->
            Log.d(TAG, "loadMeeting: response")
            showLoading(false)

            // Deserialize json using gson library
            val gson = Gson()
            val meeting = gson.fromJson(response.toString(), Array<Meeting>::class.java)
            val adapter = MeetingAdapter(meeting)
            recycler_view_meeting.adapter = adapter
        }, Response.ErrorListener { error ->
            Log.d(TAG, "loadMeeting: error")
            Log.d(TAG, "Load data error: " + error.message)
            showLoading(false)
            Toast.makeText(context!!, "Load data error.", Toast.LENGTH_LONG).show()
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

        // Add request to Queue
        requestQueue?.add(request)
    }

    private fun showLoading(state: Boolean) {
        Log.d(TAG, "showLoading: ")
        if (state) {
            progress_bar_meeting.visibility = View.VISIBLE
            recycler_view_meeting.visibility = View.INVISIBLE
        } else {
            progress_bar_meeting.visibility = View.INVISIBLE
            recycler_view_meeting.visibility = View.VISIBLE
        }
    }

    companion object{
        val TAG = "MeetingFragment"
    }

}