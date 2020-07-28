package com.example.facialattandance.dialogs

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.example.facialattandance.R
import java.util.*

class DatePickerActivity : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]
        Log.d("DatePickerFragment", "onCreateDialog: $year $month $day")
        return DatePickerDialog(activity!!, R.style.DialogTheme, activity as OnDateSetListener?, year, month, day)
    }
}