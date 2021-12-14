package com.example.chuibbo_android.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class Calc {
    fun calculateDday(end: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val endDateTime = LocalDateTime.parse(end.replace("T", " "), formatter)
        val todayDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        var difference = ChronoUnit.DAYS.between(todayDateTime.toLocalDate(), endDateTime.toLocalDate()).toInt() // 날짜만 계산

        if (endDateTime.isAfter(todayDateTime)) {
            if (endDateTime.year == todayDateTime.year && endDateTime.monthValue == todayDateTime.monthValue
                && endDateTime.dayOfMonth == todayDateTime.dayOfMonth) {
                return "D-Day"
            } else {
                return "D-$difference"
            }
        } else
            return "마감"
    }
}
