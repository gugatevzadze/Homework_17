package com.example.homework_17.data.common

sealed class Resource<T>(
    val data: T? = null,
    val errorMessage: String? = null,
    val loading: Boolean = false
) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(errorMessage: String) : Resource<T>(errorMessage = errorMessage)
    class Loading<T>(loading: Boolean) : Resource<T>(loading = loading)
}
