package com.example.chuibbo_android.api.response

data class UserResponse(
    val id: Int,
    val email: String,
    val nickname: String,
    val access_token: String,
    val refresh_token: String
)
