package com.example.chuibbo_android.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.chuibbo_android.R

class SessionManager (context: Context) {
    private var preferences: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val ACCESS_TOKEN = "access_token"
    }

    /**
     * Function to save auth token
     */
    fun saveAccessToken(token: String) {
        val editor = preferences.edit()
        editor.putString(ACCESS_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAccessToken(): String? {
        return preferences.getString(ACCESS_TOKEN, "")
    }

    fun removeAccessToken() {
        val editor = preferences.edit()
        editor.remove(ACCESS_TOKEN)
        editor.apply()
    }
}
