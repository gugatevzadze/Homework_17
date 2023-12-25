package com.example.homework_17.data.login

import com.example.homework_17.domain.login.LoginResponse

fun LoginResponseDto.toDomain(): LoginResponse {
    return LoginResponse(
        token = token
    )
}