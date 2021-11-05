package com.example.chuibbo_android.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.chuibbo_android.R

class SessionManager (context: Context) {
    private var preferences: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val USER_INFO = "user_info"
    }

    // TODO: 토큰 암호화해서 저장하는 것 고려

    fun saveAccessToken(token: String) {
        val editor = preferences.edit()
        editor.putString(ACCESS_TOKEN, token)
        editor.apply()
    }

    fun fetchAccessToken(): String? {
        return preferences.getString(ACCESS_TOKEN, "")
    }

    fun removeAccessToken() {
        val editor = preferences.edit()
        editor.remove(ACCESS_TOKEN)
        editor.apply()
    }

    fun saveUserInfo(info: String) {
        val editor = preferences.edit()
        editor.putString(USER_INFO, info)
        editor.apply()
    }

    fun fetchUserInfo(): String? {
        return preferences.getString(USER_INFO, "")
    }

    fun removeUserInfo() {
        val editor = preferences.edit()
        editor.remove(USER_INFO)
        editor.apply()
    }
}
