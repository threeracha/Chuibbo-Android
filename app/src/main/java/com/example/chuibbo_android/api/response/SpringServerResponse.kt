package com.example.chuibbo_android.api.response

data class SpringServerResponse<T>(
    val transaction_time: String?,
    val status: String?,
    val message: String?,
    val data: T? = null,
    val access_token: String? = null,
    val refresh_token: String? = null,
) {
    companion object {
        inline fun <reified T> error(transaction_time: String?, message: String? = null) =
            SpringServerResponse(transaction_time, "ERROR", message, null as T?, null, null)
    }
}
