package com.example.facialattandance.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.facialattandance.fragment.AbsentAttendeesFragment
import com.example.facialattandance.fragment.EarlyAttendeesFragment
import com.example.facialattandance.fragment.LateAttendeesFragment

class MeetingAttendeeAdapter(val fragments:Array<Fragment>, fm:FragmentManager, lifecycle: Lifecycle):FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("MeetingAttendeeAdapter", "createFragment: $position")
        return fragments.get(position)
    }
}