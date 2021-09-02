package com.example.chuibbo_android.api

import com.example.chuibbo_android.api.response.ResumePhotoUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface BackgroundApi {

    @Multipart
    @POST("/api/resume_photo/remove_background")
    fun removeBackground(
        @Part(encoding = "multipart") photo: MultipartBody.Part,
        @PartMap(encoding = "params") data: HashMap<String, RequestBody>
    ): Call<ResumePhotoUploadResponse>

    @Multipart
    @POST("/api/resume_photo/background_synthesis_solid")
    fun backgroundSynthesisSolid(
        @Part(encoding = "multipart") photo: MultipartBody.Part,
        @PartMap(encoding = "params") data: HashMap<String, RequestBody>
    ): Call<ResumePhotoUploadResponse>

    @Multipart
    @POST("/api/resume_photo/background_synthesis_gradation")
    fun backgroundSynthesisGradation(
        @Part(encoding = "multipart") photo: MultipartBody.Part,
        @PartMap(encoding = "params") data: HashMap<String, RequestBody>,
        @Part(encoding = "multipart") gradationPhoto: MultipartBody.Part,
    ): Call<ResumePhotoUploadResponse>

    companion object {
        val instance = BackgroundApiGenerator().generate(BackgroundApi::class.java)
    }
}
