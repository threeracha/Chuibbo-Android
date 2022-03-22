package com.example.chuibbo_android.api

import com.example.chuibbo_android.api.response.FlaskServerResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface BackgroundApi {

    @Multipart
    @POST("/api/resume-photos/remove-background")
    fun removeBackground(
        @Part(encoding = "multipart") photo: MultipartBody.Part,
        @PartMap(encoding = "params") data: HashMap<String, RequestBody>
    ): Call<FlaskServerResponse>

    @Multipart
    @POST("/api/resume-photos/background-synthesis-solid")
    fun backgroundSynthesisSolid(
        @Part(encoding = "multipart") photo: MultipartBody.Part,
        @PartMap(encoding = "params") data: HashMap<String, RequestBody>
    ): Call<FlaskServerResponse>

    @Multipart
    @POST("/api/resume-photos/background-synthesis-gradation")
    fun backgroundSynthesisGradation(
        @Part(encoding = "multipart") photo: MultipartBody.Part,
        @PartMap(encoding = "params") data: HashMap<String, RequestBody>,
        @Part(encoding = "multipart") gradationPhoto: MultipartBody.Part,
    ): Call<FlaskServerResponse>

    companion object {
        val instance = BackgroundApiGenerator().generate(BackgroundApi::class.java)
    }
}
