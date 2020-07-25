package com.example.facialattandance.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.facialattandance.Model.Employee
import com.example.facialattandance.Model.Meeting
import com.example.facialattandance.R
import com.example.facialattandance.adaptor.EmployeeAdaptor
import com.example.facialattandance.adaptor.MeetingAdaptor
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_meeting.*


class MeetingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_meeting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(false)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        loadMeeting()
        recycler_view_meeting.setLayoutManager(layoutManager)
    }

    private fun loadMeeting() {
        // Show progress bar and hide recycler view
        showLoading(false)

        // Initialize request queue
        val requestQueue = Volley.newRequestQueue(context)

        // Initialize request
        val url = "http://10.0.2.2:3000/meeting"
        val request = JsonArrayRequest(url, Response.Listener { response ->
            showLoading(false)

            // Deserialize json using gson library
            val gson = Gson()
            val meeting = gson.fromJson(response.toString(), Array<Meeting>::class.java)
            val adapter = MeetingAdaptor(meeting)
            recycler_view_meeting.setAdapter(adapter)
        }, Response.ErrorListener { error ->
            showLoading(false)
            Toast.makeText(context!!, "Load data error.", Toast.LENGTH_LONG).show()
            Log.d("log data", "Load data error: " + error.message)
        })

        // Add request to Queue
        requestQueue.add(request)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progress_bar_meeting.setVisibility(View.VISIBLE)
            recycler_view_meeting.setVisibility(View.INVISIBLE)
        } else {
            progress_bar_meeting.setVisibility(View.INVISIBLE)
            recycler_view_meeting.setVisibility(View.VISIBLE)
        }
    }

}