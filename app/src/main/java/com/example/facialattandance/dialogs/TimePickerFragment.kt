package com.example.facialattandance.dialogs


import android.app.Dialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.facialattandance.R

class TimePickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
//        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
        return TimePickerDialog(activity!!, R.style.DialogTheme, targetFragment as TimePickerDialog.OnTimeSetListener?, hour, minute, false)
    }

}