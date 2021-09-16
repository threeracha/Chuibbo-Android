package com.example.chuibbo_android.api

import com.example.chuibbo_android.api.response.ApiResponse
import com.example.chuibbo_android.api.response.SpringResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface UserApi {

    @POST("/api/v1/user/join")
    fun signup(
        @Body data: HashMap<String, String>
    ): Call<SpringResponse<String>>

    @GET("/api/v1/user/login")
    fun login(
        @PartMap(encoding = "params") data: HashMap<String, RequestBody>
    ): Call<ApiResponse<String>>

    @GET("/api/v1/user/logout")
    fun logout(): Call<ApiResponse<String>>

    @GET("/api/v1/user/login/google")
    fun loginGoogle(): Call<ApiResponse<String>>

    @GET("/api/v1/user/login/kakao")
    fun loginKakao(
        @PartMap(encoding = "params") data: HashMap<String, RequestBody>
    ): Call<ApiResponse<String>>

    @GET("/api/v1/user/login/naver")
    fun loginNaver(
        @PartMap(encoding = "params") data: HashMap<String, RequestBody>
    ): Call<ApiResponse<String>>

    @GET("/api/v1/user/info/{userEmail}")
    fun userInfo(
        @Path("userEmail") userEmail: String,
    ): Call<ApiResponse<String>>

    @DELETE("/api/v1/user/withdraw/{userId}")
    fun withdraw(
        @Path("userId") userId: Int
    ): Call<ApiResponse<String>>

    @POST("/api/v1/user/find-password")
    fun findPassword(
        @Body data: HashMap<String, RequestBody>,
    ): Call<ApiResponse<String>>

    companion object {
        val instance = ApiGenerator().generateSpring(UserApi::class.java)
    }
}
