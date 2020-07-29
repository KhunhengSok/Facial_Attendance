package com.example.facialattandance.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.facialattandance.R
import kotlinx.android.synthetic.main.fragment_early_attendees.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AbsentAttendeesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AbsentAttendeesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_absent_attendees, container, false)
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
        val TAG = "AbsentAttendeesFragment"

    }
}