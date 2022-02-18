package com.example.chuibbo_android.api

import android.content.Context
import com.example.chuibbo_android.mypage.PhotoAlbum
import retrofit2.Call
import retrofit2.http.GET

interface ResumePhotoApi {

    @GET("/api/v1/resume-photo/photos")
    fun getResumePhotos() : Call<List<PhotoAlbum>>

    companion object {
        fun instance(context: Context): ResumePhotoApi {
            return ApiGenerator().generateSpring(ResumePhotoApi::class.java, context)
        }
    }
}
