package com.example.homework_17.domain.login

import com.example.homework_17.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(username: String, password: String): Flow<Resource<LoginResponse>>
}