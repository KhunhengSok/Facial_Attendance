package com.example.facialattandance.fragment




//import jdk.nashorn.internal.objects.NativeDate.getTime
//import sun.jvm.hotspot.utilities.IntArray

//import sun.jvm.hotspot.utilities.IntArray

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.facialattandance.Model.AttandaceTime
import com.example.facialattandance.Model.DatePickerFragment
import com.example.facialattandance.Model.TimePickerFragment
import com.example.facialattandance.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_setup.*
import kotlinx.android.synthetic.main.view_holder_employee.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class SetupFragment :  Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener  {

    var dates = ""
    var months = ""
    var years = ""
    var hour = ""
    var minutePicker = ""
    var isStart = false



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(false)
        startDate.setOnClickListener {
            val datePicker: DialogFragment = DatePickerFragment()
            datePicker.setTargetFragment(this@SetupFragment, 0)
            datePicker.show(fragmentManager!!, "date picker")

        }
        startingtime.setOnClickListener {
            isStart = true
            val datePicker: DialogFragment = TimePickerFragment()
            datePicker.setTargetFragment(this@SetupFragment, 0)
            datePicker.show(fragmentManager!!, "time picker")
        }
        latetime.setOnClickListener {
            isStart = false
            val datePicker: DialogFragment = TimePickerFragment()
            datePicker.setTargetFragment(this@SetupFragment, 0)
            datePicker.show(fragmentManager!!, "time picker")
        }

        createTime.setOnClickListener {
            var title = title.text.toString()
            var start = startingtime.text.toString()
            var late = latetime.text.toString()
            var date = startDate.text.toString()
            if (title.equals("Title") || start.equals("Starting Time")|| late.equals("Late Time") || date.equals("Date")) {
                Toast.makeText(context!!, "Please Complete requirement", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener;
            } else {
                val time = AttandaceTime(title, date, start, late)
                createSlot(title, date, start, late)
            }
        }
    }




    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val c = Calendar.getInstance()
        val pattern = "dd-MM-yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = month
        c[Calendar.DAY_OF_MONTH] = dayOfMonth
        val currentDate: String = simpleDateFormat.format(c.getTime())
        dates = Integer.toString(dayOfMonth)
        months = Integer.toString(month)
        years = Integer.toString(year)
        startDate.setText(currentDate)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val c = Calendar.getInstance()
        val pattern = "hh-mm-a"
        val simpleDateFormat = SimpleDateFormat(pattern)
        c[Calendar.HOUR_OF_DAY] = hourOfDay
        c[Calendar.MINUTE] = minute
        val currentTime: String = simpleDateFormat.format(c.getTime())
        hour = Integer.toString(hourOfDay)
        minutePicker = Integer.toString(minute)
        if (isStart == true) {
            startingtime.setText(currentTime)
        } else {
            latetime.setText(currentTime)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBarSetup.visibility = View.VISIBLE
        } else {
            progressBarSetup.visibility = View.INVISIBLE
        }
    }


    private fun createSlot(title: String, date:String, start: String, late: String) {
        val request: RequestQueue = Volley.newRequestQueue(context!!)
        val url = "http://10.0.2.2:3000/attandace";

        //String Request initialized
        var mStringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
            Toast.makeText(context!!, "Successfully", Toast.LENGTH_SHORT).show()

//            val gson = Gson()
//            val attandaceTimeJson = gson.toJson(attandaceTime)

        }, Response.ErrorListener { error ->
            Log.i("This is the error", "Error :$error")
//            Toast.makeText(context!!, "Please make sure you enter correct password and username", Toast.LENGTH_SHORT).show()
        }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            override fun getBody(): ByteArray {
                val params2 = HashMap<String, String>()
                params2.put("title",title )
                params2.put("date", date)
                params2.put("startingTime",start )
                params2.put("lateTime", late)
                return JSONObject(params2 as Map<*, *>).toString().toByteArray()
            }
        }
        request!!.add(mStringRequest!!)
    }
}
