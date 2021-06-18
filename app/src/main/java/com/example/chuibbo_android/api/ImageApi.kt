package com.example.chuibbo_android.api

import com.example.chuibbo_android.api.request.*
import com.example.chuibbo_android.api.response.ApiResponse
import com.example.chuibbo_android.api.response.ResumePhotoUploadResponse
import okhttp3.MultipartBody
import retrofit2.Call
//import okhttp3.Response
import retrofit2.http.*
import retrofit2.Response

interface ImageApi {
    // 테스트
    @GET("/api/hello")
    suspend fun hello(): ApiResponse<String>

    // 이미지
    @Multipart
    @POST("/api/resume_photo/")
    fun uploadResumePhoto(
            @Part image: MultipartBody.Part
    ) : Call<String>

    companion object {
        val instance = ApiGenerator()
            .generate(ImageApi::class.java)
    }
}