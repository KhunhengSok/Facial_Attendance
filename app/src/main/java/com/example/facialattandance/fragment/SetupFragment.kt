package com.example.facialattandance.fragment




//import jdk.nashorn.internal.objects.NativeDate.getTime
//import sun.jvm.hotspot.utilities.IntArray

//import sun.jvm.hotspot.utilities.IntArray

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.facialattandance.Model.DatePickerFragment
import com.example.facialattandance.Model.TimePickerFragment
import com.example.facialattandance.R
import kotlinx.android.synthetic.main.fragment_setup.*
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
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val c = Calendar.getInstance()
        val pattern = "dd-MM-yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = month
        c[Calendar.DAY_OF_MONTH] = dayOfMonth
        val i = c.getTime()
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
        val i = c.getTime()
        val currentTime: String = simpleDateFormat.format(c.getTime())
        hour = Integer.toString(hourOfDay)
        minutePicker = Integer.toString(minute)
        if (isStart == true) {
            startingtime.setText(currentTime)
        } else {
            latetime.setText(currentTime)
        }

    }



}
