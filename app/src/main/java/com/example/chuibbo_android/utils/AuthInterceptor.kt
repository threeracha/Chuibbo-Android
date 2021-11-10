package com.example.chuibbo_android.utils

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

        // TODO: 기간 만료시, refresh_token 발급

        return chain.proceed(requestBuilder.build())
    }
}
