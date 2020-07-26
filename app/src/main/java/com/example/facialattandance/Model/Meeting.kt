package com.example.facialattandance.Model

import com.google.gson.annotations.SerializedName

class Meeting(
    val id:Int,
    val name:String,
    val date:String,
    val start_time:String,
    val end_time:String,
    @SerializedName("organization_id")
    val departmentId:String,
    @SerializedName("attendees")
    val attendees:Array<String>
)