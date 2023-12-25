package com.example.homework_17.data.register

import com.example.homework_17.domain.register.RegisterResponse

fun RegisterResponseDto.toDomain(): RegisterResponse {
    return RegisterResponse(
        id = id,
        token = token
    )
}