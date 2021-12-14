package com.example.chuibbo_android.api

import com.example.chuibbo_android.api.response.FlaskServerResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ImageApi {
    // 테스트
    @GET("/api/hello")
    suspend fun hello(): FlaskServerResponse

    // 취업 사진
    @Multipart
    @POST("/api/resume_photo/")
    fun uploadResumePhoto(
        @Part(encoding = "multipart") photo: MultipartBody.Part,
        @PartMap(encoding = "params") data: HashMap<String, RequestBody>
    ): Call<FlaskServerResponse>

    companion object {
        val instance = ApiGenerator()
            .generate(ImageApi::class.java)
    }
}
