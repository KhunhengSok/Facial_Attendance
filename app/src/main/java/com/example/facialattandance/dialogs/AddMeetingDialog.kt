package com.example.facialattandance.dialogs

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.facialattandance.Activity.SplashScreenActivity
import com.example.facialattandance.AddEmployeeActivity
import com.example.facialattandance.R
import com.example.facialattandance.utils.HOSTING_URL
import kotlinx.android.synthetic.main.dialog_add_meeting.*
import kotlinx.android.synthetic.main.dialog_add_meeting.view.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AddMeetingDialog: DialogFragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_add_meeting, null)
//        builder.setView(R.layout.dialog_add_meeting)
        builder.setView(view)
        init(view)
        return builder.create()
    }


    var isStart = false
    var dates = ""
    var months = ""
    var years = ""
    var hour = ""
    var minutePicker = ""
    val start_time = ""
    val end_time = ""

    fun createMeeting(){
        val POST_MEETING_URL = HOSTING_URL + "api/event/create"
        val json = JSONObject()
        json.put("name", "")
        json.put("organization", SplashScreenActivity.currentOrganization!!.name)
        json.put("date", dates)
        json.put("start_time", "start_time")
        json.put("end_time", "end_time")

//        val request = JsonObjectRequest(Request.Method.POST, )
//        requestQueue.add(request)
    }

    private fun init(view:View){
        Log.d("AddMeetingDialog", "init: ")

        view.date_picker_button?.setOnClickListener{
            Log.d("AddMeetingDialog", "init: clicked")
            val datePicker: DialogFragment = DatePickerFragment()
            datePicker.setTargetFragment(targetFragment, 0)
            datePicker.show(fragmentManager!!, "date picker")
        }

        view.meeting_start_time_button?.setOnClickListener{
            isStart = true
            val datePicker: DialogFragment = TimePickerFragment()
            datePicker.setTargetFragment(targetFragment, 0)
            datePicker.show(fragmentManager!!, "time picker")
        }

        view.meeting_end_time_button?.setOnClickListener{
            isStart = false
            val datePicker: DialogFragment = TimePickerFragment()
            datePicker.setTargetFragment(targetFragment, 0)
            datePicker.show(fragmentManager!!, "time picker")
        }

        view.create_button?.setOnClickListener{
            createMeeting()
            Log.d("AddMeetingDialog", "init: create")
            dialog?.dismiss()
        }

        view.cancel_button?.setOnClickListener{
            Log.d("AddMeetingDialog", "init: cancel")
            dialog?.dismiss()
        }

    }



    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
       /* Log.d("AddMeetingDialog", "onDateSet: ")
        val c = Calendar.getInstance()
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = month
        c[Calendar.DAY_OF_MONTH] = dayOfMonth
        Log.d(TAG, "onDateSet: $month")
        val currentDate: String = simpleDateFormat.format(c.getTime())*/
//        dates = Integer.toString(dayOfMonth)
//        months = Integer.toString(month)
//        years = Integer.toString(year)

//        date_picker_button.text = currentDate
//                date_picker_button?.setText(currentDate)
        Log.d(AddEmployeeActivity.TAG, "onDateSet: ")

        Log.d("AddMeetingDialog", "onDateSet: ")
        val c = Calendar.getInstance()
//        val pattern = "dd-MM-yyyy"
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = month
        c[Calendar.DAY_OF_MONTH] = dayOfMonth
        val currentDate: String = simpleDateFormat.format(c.getTime())
        Log.d("AddMeetingDialog", "onDateSet: $currentDate")
        date_picker_button?.text = currentDate
    }


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Log.d(TAG, "onTimeSet: ")
        val c = Calendar.getInstance()
        val pattern = "hh-mm-a"
        val simpleDateFormat = SimpleDateFormat(pattern)
        c[Calendar.HOUR_OF_DAY] = hourOfDay
        c[Calendar.MINUTE] = minute
        val currentTime: String = simpleDateFormat.format(c.getTime())
        hour = Integer.toString(hourOfDay)
        minutePicker = Integer.toString(minute)

        if (isStart == true) {
            meeting_start_time_button.setText(currentTime)
        } else {
            meeting_end_time_button?.setText(currentTime)
        }
    }

    companion object{
        val TAG = "AddMeetingDialog"
    }
}