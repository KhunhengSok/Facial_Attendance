package com.example.facialattandance.fragment

import android.content.Intent
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
import com.example.facialattandance.AddEmployeeActivity
import com.example.facialattandance.Model.Employee
import com.example.facialattandance.R
import com.example.facialattandance.adapter.EmployeeAdapter
import com.example.facialattandance.utils.HOSTING_URL
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_employee.*
import kotlinx.android.synthetic.main.activity_employee.progress_bar
import kotlinx.android.synthetic.main.activity_employee.recycler_view
import kotlinx.android.synthetic.main.fragment_employee.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployeeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmployeeFragment : Fragment() {
    private var Employee_Url: String? = null
    private var organizationId = SplashScreenActivity.currentDepartment!!.id
    private var requestQueue: RequestQueue?=null

    fun init(){
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        recycler_view!!.layoutManager = layoutManager
        Employee_Url = HOSTING_URL + "api/organization/${SplashScreenActivity.currentDepartment!!.id}/employees"
        Log.d(EmployeeActivity.TAG, "init: ${Employee_Url}")
        requestQueue = Volley.newRequestQueue(context)

        add_employee_fab.setOnClickListener{
            val intent = Intent(activity, AddEmployeeActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        loadEmployee()

    }

    private fun loadEmployee() {
        // Show progress bar and hide recycler view
        showLoading(true)

        val request = object: JsonArrayRequest(Employee_Url, Response.Listener { response ->
            Log.d(EmployeeActivity.TAG, "loadEmployee: Response")
            showLoading(false)

            // Deserialize json using gson library
            val gson = Gson()
            val employees = gson.fromJson(response.toString(), Array<Employee>::class.java)
            val adapter = EmployeeAdapter(employees)
            recycler_view?.adapter = adapter
        }, Response.ErrorListener { error ->
            showLoading(false)
            Toast.makeText(activity, "Load data error.", Toast.LENGTH_SHORT).show()
            Log.d(EmployeeActivity.TAG, "Load data error: " + error.message)
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
        requestQueue!!.add(request)
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
        val TAG = "EmployeeFragment"

    }
}