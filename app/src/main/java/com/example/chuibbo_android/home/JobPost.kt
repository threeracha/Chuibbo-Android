package com.example.chuibbo_android.home

import java.time.LocalDateTime
import java.util.*

// TODO: DB 컬럼명과 동일하도록 수정
data class JobPost(
    val id: Int,
    val companyName: String,
    val companyDesc: String,
    val companyDeadline: Int,
    val companyLogo: String,
    val companyLink: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime
)
