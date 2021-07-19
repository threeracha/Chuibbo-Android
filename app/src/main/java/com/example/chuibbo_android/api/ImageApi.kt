package com.example.chuibbo_android.api

import com.example.chuibbo_android.api.response.ApiResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageApi {
    // 테스트
    @GET("/api/hello")
    suspend fun hello(): ApiResponse<String>

    // 이미지
    @Multipart
    @POST("/api/resume_photo/")
    fun uploadResumePhoto(
        @Part image: MultipartBody.Part
    ): Call<ResponseBody>

    companion object {
        val instance = ApiGenerator()
            .generate(ImageApi::class.java)
    }
}
