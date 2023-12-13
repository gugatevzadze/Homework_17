package com.example.homework_17.service

import com.example.homework_17.login.LoginRequest
import com.example.homework_17.login.LoginResponse
import com.example.homework_17.register.RegisterRequest
import com.example.homework_17.register.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    //suspend function to post login
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
    //suspend function to post register
    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse
}