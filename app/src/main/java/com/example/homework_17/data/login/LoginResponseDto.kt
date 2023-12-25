package com.example.homework_17.data.login

import com.squareup.moshi.Json

data class LoginResponseDto(
    @Json(name = "token")
    val token: String
)