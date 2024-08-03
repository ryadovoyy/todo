package com.ryadovoy.todo.data

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val exception: Throwable) : ApiResponse<Nothing>()
}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResponse<T> {
    return try {
        ApiResponse.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        ApiResponse.Error(throwable)
    }
}
