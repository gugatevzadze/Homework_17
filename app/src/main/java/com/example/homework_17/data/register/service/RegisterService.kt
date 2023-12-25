package com.example.homework_17.data.register.service

import com.example.homework_17.data.register.RegisterResponseDto
import com.example.homework_17.presentation.register.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponseDto>
}