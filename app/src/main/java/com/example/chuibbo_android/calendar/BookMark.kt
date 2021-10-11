package com.example.chuibbo_android.calendar

import com.example.chuibbo_android.home.JobPost

data class BookMark(
    val id: Long,
    val userId: Long,
    val jobPost: JobPost?
)
