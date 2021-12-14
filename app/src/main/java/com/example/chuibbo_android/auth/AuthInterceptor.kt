package com.example.chuibbo_android.auth

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        sessionManager.fetchAccessToken()?.let {
            requestBuilder.addHeader("Authorization", "$it")
        }

        val response = chain.proceed(requestBuilder.build())
        when (response.code) {
            400 -> {

            }
            401 -> { // TODO: access_token 만료시, refresh_token으로 access_token 발급
                val refreshToken = sessionManager.fetchRefreshToken()

                // TODO: pref 업데이트
                sessionManager.saveAccessToken("")
            }
            // TODO: refresh_token 만료 시,
        }

        // TODO: 회원 정보가 변경되어도 datasource에 정보 그대로 남아있는 문제

        return response
    }
}
