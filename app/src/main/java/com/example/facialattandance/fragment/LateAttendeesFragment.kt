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
import kotlinx.android.synthetic.main.fragment_early_attendees.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LateAttendeesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LateAttendeesFragment : Fragment() {
    var requestQueue: RequestQueue?= null
    var LateUrl = ""

    fun init(){
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        recycler_view!!.layoutManager = layoutManager
        Log.d(EmployeeActivity.TAG, "init: ${LateUrl}")
        requestQueue = Volley.newRequestQueue(context)


    }

    private fun loadEmployee() {
        // Show progress bar and hide recycler view
        showLoading(true)

        val request = object: JsonArrayRequest(LateUrl, Response.Listener { response ->
            Log.d(EarlyAttendeesFragment.TAG, "loadEmployee: Response")
            showLoading(false)


            // Deserialize json using gson library
            val gson = Gson()
            val employees = gson.fromJson(response.toString(), Array<Employee>::class.java)
//            Log.d(TAG, "loadEmployee: ${employees[0].name}")
            if(employees.isNotEmpty()){
                val adapter = EmployeeAdapter(employees)
                recycler_view?.adapter = adapter
                Log.d(EarlyAttendeesFragment.TAG, "loadEmployee: set adapter")
            }else{
                none_indicator.visibility = View.VISIBLE
                none_indicator.text  = "None"
            }
        }, Response.ErrorListener { error ->
            showLoading(false)
            Toast.makeText(activity, "Load data error.", Toast.LENGTH_SHORT).show()
            Log.d(EarlyAttendeesFragment.TAG, "Load data error: " + error.message)
        }){
            override fun getHeaders(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                if(SplashScreenActivity.currentUser == null){
                    SplashScreenActivity.currentUser = SplashScreenActivity.retrieveUser()
                }
                Log.d(EarlyAttendeesFragment.TAG, "getHeaders: token: ${SplashScreenActivity.currentUser!!.token}")
                params.put("Content-Type", "application/json")
                params.put("Authorization", "Bearer " + SplashScreenActivity.currentUser!!.token)
                return params
            }
        }


        // Add request to Queue
        requestQueue!!.add(request)
    }

    override fun onResume() {
        super.onResume()
        loadEmployee()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_late_attendees, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id =  arguments?.get("id")

        LateUrl = HOSTING_URL + "api/event/$id/late"
        init()
        loadEmployee()
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
        val TAG = "LateAttendeesFragment"

    }
}