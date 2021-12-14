package com.example.chuibbo_android.api

import com.example.chuibbo_android.api.request.MakeupRequest
import com.example.chuibbo_android.api.request.MakeupStrongRequest
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface MakeupApi {
    @GET
    fun downloadFile(@Url fileUrl:String): Call<ResponseBody>

    @Multipart
    @POST("/api/upload")
    fun uploadFile(@Part file: MultipartBody.Part): Call<String>

    @POST("/api/parameter")
    fun uploadParameter(@Body makeupRequest: MakeupRequest): Call<MakeupRequest>

    @POST("/api/strong")
    fun uploadStrong(@Body strong: Int): Call<MakeupStrongRequest>

    @POST("/api/makeup")
    fun makeUpFace(@Body makeupRequest: MakeupRequest): Call<String>

    companion object {
        val instance = ApiGenerator().generate(MakeupApi::class.java)
    }
}
