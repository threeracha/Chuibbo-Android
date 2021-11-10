package com.example.chuibbo_android.api.response

data class SpringResponse2<T>(
    val transaction_time: String?,
    val status: String?,
    val message: String?,
    val data: T? = null,
    val access_token: String? = null,
    val refresh_token: String? = null,
) {
    companion object {
        inline fun <reified T> error(description: String? = null) =
            SpringResponse("", "ERROR", "ERROR", null as T?, null, null)
    }
}
