package com.example.homework_17.data.login.service

import com.example.homework_17.data.login.LoginResponseDto
import com.example.homework_17.presentation.login.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    //suspend function to post login
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponseDto>
}