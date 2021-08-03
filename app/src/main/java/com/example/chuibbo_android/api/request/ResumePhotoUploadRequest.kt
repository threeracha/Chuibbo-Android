package com.example.chuibbo_android.api.request

data class ResumePhotoUploadRequest(
    val id: String, // 유저 아이디
    val sex: Int,
    val face_shape: Int,
    val hairstyle: String,
    val prev_hairstyle: String,
    val suit: Int
)
