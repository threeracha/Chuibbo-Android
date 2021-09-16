package com.example.chuibbo_android.api.response

data class SpringResponse<T>(
    val transaction_time: String?,
    val result_code: String?,
    val description: String?,
    val data: T? = null,
    val token: String? = null
) {
    companion object {
        inline fun <reified T> error(description: String? = null) =
            SpringResponse("", "ERROR", "ERROR", null as T?, null)
    }
}
