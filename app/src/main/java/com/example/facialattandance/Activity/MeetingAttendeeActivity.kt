package com.example.facialattandance.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.facialattandance.R
import com.example.facialattandance.adapter.MeetingAttendeeAdapter
import com.example.facialattandance.fragment.AbsentAttendeesFragment
import com.example.facialattandance.fragment.EarlyAttendeesFragment
import com.example.facialattandance.fragment.LateAttendeesFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_event_attendees.*
import kotlinx.android.synthetic.main.fragment_camera.*

class MeetingAttendeeActivity : AppCompatActivity() {
    companion object {
        const val MEETING_ID_KEY = "MEETING_ID"
        val TAG = "MeetingAttendeeActivity"
    }
    var id = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meeting_attendee)
        Log.d(TAG, "onCreate: ")

        val meetingName = intent.getStringExtra("MeetingName")
        id = intent.getIntExtra(MEETING_ID_KEY, 0)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener{
            this.onBackPressed()
        }
        supportActionBar?.title = meetingName


        val bundle = Bundle()
        bundle.putInt("id", id)
        val early = EarlyAttendeesFragment()
        early.arguments = bundle
        val late = LateAttendeesFragment()
        late.arguments = bundle
        val absent = AbsentAttendeesFragment()
        absent.arguments = bundle

        val fragments = arrayOf(
                early,
                late,
                absent
        )

        val names = arrayOf(
                "Early",
                "Late",
                "Absent"
        )

        val adapter = MeetingAttendeeAdapter(fragments, supportFragmentManager, lifecycle)
        viewPager?.adapter = adapter

        TabLayoutMediator(tab_layout as TabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = names.get(position)
        }).attach()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        LayoutInflater.from(this).inflate(R.menu.menu_meeting, menu)
        menuInflater.inflate(R.menu.menu_meeting, menu)
        return true
//        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.camera -> {
                Log.d(TAG, "onOptionsItemSelected: clicked")
                val intent = Intent(baseContext, CameraActivity::class.java)
                intent.putExtra(CameraActivity.CAMERA_MODE_KEY, CameraActivity.SCANNING_MODE)
                intent.putExtra("EventId", id)
                startActivity(intent)
            }

        }
        return true

    }
}