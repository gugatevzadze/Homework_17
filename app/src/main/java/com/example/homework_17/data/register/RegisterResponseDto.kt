package com.example.homework_17.data.register

import com.squareup.moshi.Json

data class RegisterResponseDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "token")
    val token: String
)