package com.example.facialattandance.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.facialattandance.Activity.EmployeeActivity
import com.example.facialattandance.R
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    private val REQUEST_CODE = 10000

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bestEmployee.setOnClickListener {
            val intent = Intent(context!!,EmployeeActivity::class.java)
            intent.putExtra("types", "BestEmployee")
            startActivityForResult(intent, REQUEST_CODE)
        }

        lateEmployee.setOnClickListener{
            val intent = Intent(context!!,EmployeeActivity::class.java)
            intent.putExtra("types", "LateEmployee")
            startActivityForResult(intent, REQUEST_CODE)
        }
    }



}
