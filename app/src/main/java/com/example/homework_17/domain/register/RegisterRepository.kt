package com.example.homework_17.domain.register

import com.example.homework_17.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(username: String, password: String): Flow<Resource<RegisterResponse>>
}