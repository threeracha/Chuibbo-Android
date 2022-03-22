package com.example.chuibbo_android.api

import android.content.Context
import com.example.chuibbo_android.api.request.LoginRequest
import com.example.chuibbo_android.api.request.SignupRequest
import com.example.chuibbo_android.api.response.SpringResponse
import com.example.chuibbo_android.api.response.UserResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.HashMap

interface UserApi {

    @POST("/api/v1/users/signup")
    fun signup(
        @Body signupRequest: SignupRequest
    ): Call<SpringResponse<String>>

    @GET("/api/v1/users/nickname")
    fun checkNickname(
        @Query("nickname") nickname: String
    ): Call<SpringResponse<String>>

    @GET("/api/v1/users/email")
    fun checkEmail(
        @Query("email") email: String
    ): Call<SpringResponse<String>>

    @POST("/api/v1/users/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<UserResponse>

    @GET("/api/v1/users/logout")
    fun logout(): Call<SpringResponse<String>>

    @GET("/api/v1/users/login/google")
    fun loginGoogle(): Call<SpringResponse<String>>

    @GET("/api/v1/users/login/kakao")
    fun loginKakao(
        @PartMap(encoding = "params") data: HashMap<String, RequestBody>
    ): Call<SpringResponse<String>>

    @GET("/api/v1/users/login/naver")
    fun loginNaver(
        @PartMap(encoding = "params") data: HashMap<String, RequestBody>
    ): Call<SpringResponse<String>>

    @GET("/api/v1/users/info")
    fun userInfo(): Call<SpringResponse<UserResponse>>

    @DELETE("/api/v1/users/withdrawal")
    fun withdraw(): Call<SpringResponse<String>>

    @POST("/api/v1/users/password")
    fun findPassword(
        @Body email: RequestBody,
    ): Call<SpringResponse<String>>

    @PUT("/api/v1/users/password")
    fun changePassword(
        @Body password: RequestBody,
    ): Call<SpringResponse<String>>

    companion object {
        fun instance(context: Context): UserApi {
            return ApiGenerator().generateSpring(UserApi::class.java, context)
        }
    }
}
