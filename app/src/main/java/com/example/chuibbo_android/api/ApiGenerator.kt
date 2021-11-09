package com.example.chuibbo_android.api

import android.content.Context
import com.example.chuibbo_android.utils.AuthInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiGenerator {
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    fun <T> generate(api: Class<T>): T = Retrofit.Builder()
        .baseUrl(HOST)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
        .create(api)

    fun <T> generateSpring(api: Class<T>, context: Context): T = Retrofit.Builder()
        .baseUrl(SPRING_HOST)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient(context))
        .build()
        .create(api)

    fun <T> generateSpring2(api: Class<T>): T = Retrofit.Builder()
        .baseUrl(SPRING_HOST)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
        .create(api)

    var okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private fun okHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }

    companion object {
        const val HOST = "http://10.0.2.2:5000"
        const val SPRING_HOST = "http://10.0.2.2:7000"
    }
}
