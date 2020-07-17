package com.example.facialattandance.Model

class AttandaceTime(title: String, date: String, startingTime: String, lateTime: String) {
    private var title: String? = null
    private var date: String? = null
    private var startingTime: String? = null
    private var lateTime: String? = null



    init {
        this.title = title
        this.date = date
        this.lateTime = lateTime
        this.startingTime = startingTime
    }



}
