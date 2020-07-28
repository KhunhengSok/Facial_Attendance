package com.example.facialattandance.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.facialattandance.Activity.EmployeeActivity
import com.example.facialattandance.Activity.SplashScreenActivity
import com.example.facialattandance.dialogs.DatePickerFragment
import com.example.facialattandance.Model.Meeting
import com.example.facialattandance.dialogs.TimePickerFragment
import com.example.facialattandance.R
import com.example.facialattandance.adapter.MeetingAdapter
import com.example.facialattandance.dialogs.AddMeetingDialog
import com.example.facialattandance.utils.HOSTING_URL
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialog_add_meeting.*
import kotlinx.android.synthetic.main.fragment_meeting.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class MeetingFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private var organizationId = SplashScreenActivity.currentOrganization!!.id
    private var Meeting_Url = HOSTING_URL + "api/organization/$organizationId/events"
    private var requestQueue:RequestQueue ?= null
    private var dialog:Dialog ?= null

    fun init(){
        add_new_meeting_fab.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "init: onclick")
            showDialog()
            dialogInit()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_meeting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(false)
        requestQueue =  Volley.newRequestQueue(context)
        init()
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
            recycler_view_meeting?.adapter = adapter
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
        Log.d(TAG, "showLoading: $state")
        if (state) {
            progress_bar_meeting.visibility = View.VISIBLE
//            recycler_view_meeting.visibility = View.INVISIBLE
        } else {
            progress_bar_meeting?.visibility = View.INVISIBLE
//            recycler_view_meeting?.visibility = View.VISIBLE
        }

    }

    companion object{
        val TAG = "MeetingFragment"
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.d("AddMeetingDialog", "onDateSet: ")
        val c = Calendar.getInstance()
        val pattern = "yyyy-mm-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = (month + 1 )
        c[Calendar.DAY_OF_MONTH] = dayOfMonth
        val currentDate: String = simpleDateFormat.format(c.getTime())
        dates = Integer.toString(dayOfMonth)
        months = Integer.toString(month)
        years = Integer.toString(year)

        Log.d("AddMeetingDialog", "onDateSet: $currentDate")
        dialog?.date_picker_button?.text = currentDate
    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Log.d(AddMeetingDialog.TAG, "onTimeSet: ")
        val c = Calendar.getInstance()
        val pattern = "hh:mm:a"
        val simpleDateFormat = SimpleDateFormat(pattern)
        c[Calendar.HOUR_OF_DAY] = hourOfDay
        c[Calendar.MINUTE] = minute
        val currentTime: String = simpleDateFormat.format(c.getTime())
        hour = Integer.toString(hourOfDay)
        minutePicker = Integer.toString(minute)

        if (isStart == true) {
            dialog?.meeting_start_time_button?.setText(currentTime)
        } else {
            dialog?.meeting_end_time_button?.setText(currentTime)
        }
    }



    var isStart = false
    var dates = ""
    var months = ""
    var years = ""
    var hour = ""
    var minutePicker = ""
    val start_time = ""
    val end_time = ""

    fun createMeeting(name:String, date:String, start_time:String, end_time:String){
        showLoading(true)

        val POST_MEETING_URL = HOSTING_URL + "api/event/create"
        val json = JSONObject()
        json.put("name", name)
        json.put("organization", SplashScreenActivity.currentOrganization!!.name)
        json.put("date", date)
        json.put("start_time", start_time)
        json.put("end_time", end_time)

        Log.d(TAG, "createMeeting: $json")
        val request = object:JsonObjectRequest(Request.Method.POST, POST_MEETING_URL, json, Response.Listener {
            response ->
                run {
                    Log.d(TAG, "createMeeting: response")
                    Log.d(TAG, "createMeeting: $response")
                    showLoading(false)
                    loadMeeting()
                    Toast.makeText(activity, "Successfully added", Toast.LENGTH_SHORT).show()
                }
        }, Response.ErrorListener {
            error ->
                run {
                    Log.d(TAG, "createMeeting: error")
                    Log.d(TAG, "createMeeting: $error")
                    showLoading(false)
                }
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
        requestQueue?.add(request)
    }

    fun showDialog(){
        dialog = Dialog(context!!)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE) // before

        dialog?.setContentView(R.layout.dialog_add_meeting)
        dialog?.setCancelable(true)

        dialog?.date_picker_button?.setOnClickListener{
            Log.d(TAG, "showDialog: clikced")
        }

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog?.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialogInit()
        dialog?.show()
        dialog?.window!!.attributes = lp
    }

    fun dialogInit(){
        dialog?.date_picker_button?.setOnClickListener{
            val datePicker: DialogFragment = DatePickerFragment()
            datePicker.setTargetFragment(this@MeetingFragment, 0)
            datePicker.show(fragmentManager!!, "date picker")
        }

        dialog?.meeting_start_time_button?.setOnClickListener{
            isStart = true
            val datePicker: DialogFragment = TimePickerFragment()
            datePicker.setTargetFragment(this@MeetingFragment, 0)
            datePicker.show(fragmentManager!!, "time picker")
        }

        dialog?.meeting_end_time_button?.setOnClickListener{
            isStart = false
            val datePicker: DialogFragment = TimePickerFragment()
            datePicker.setTargetFragment(this@MeetingFragment, 0)
            datePicker.show(fragmentManager!!, "time picker")
        }

        dialog?.create_button?.setOnClickListener{
            val date = dialog?.date_picker_button?.text.toString()
            val start_time = dialog?.meeting_start_time_button?.text.toString()
            val end_time = dialog?.meeting_end_time_button?.text.toString()
            val meeting_name = dialog?.meeting_name?.text.toString()
            Log.d(TAG, "dialogInit: $date")
            Log.d(TAG, "dialogInit: $start_time")
            Log.d(TAG, "dialogInit: $end_time")
            Log.d(TAG, "dialogInit: $meeting_name")
            if(!date.isNullOrBlank() && !start_time.isNullOrBlank() && !end_time.isNullOrBlank() && !meeting_name.isNullOrBlank()){
                createMeeting(meeting_name!!, date!!, start_time, end_time)
                dialog?.dismiss()
            }else{
                Toast.makeText(activity, "All fields are required.", Toast.LENGTH_SHORT).show()
            }

        }

        dialog?.cancel_button?.setOnClickListener{
            dialog?.dismiss()
        }

    }


}