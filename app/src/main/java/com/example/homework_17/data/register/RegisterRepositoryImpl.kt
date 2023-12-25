package com.example.homework_17.data.register

import com.example.homework_17.data.common.Resource
import com.example.homework_17.data.register.service.RegisterService
import com.example.homework_17.datastore.DataStoreUtil
import com.example.homework_17.domain.register.RegisterRepository
import com.example.homework_17.domain.register.RegisterResponse
import com.example.homework_17.presentation.register.RegisterRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class RegisterRepositoryImpl@Inject constructor(private val registerService: RegisterService,
                                                private val dataStoreUtil: DataStoreUtil
): RegisterRepository {
    override suspend fun register(
        username: String,
        password: String
    ): Flow<Resource<RegisterResponse>> {
        return flow {
            emit(Resource.Loading(loading = true))
            try {
                val response: Response<RegisterResponseDto> = registerService.register(
                    RegisterRequest(username, password)
                )
                emit(Resource.Success(data = response.body()!!.toDomain()))
                dataStoreUtil.saveUserEmail(username)
            } catch (e: HttpException) {
                //handle HTTP error responses
               emit(Resource.Error("HTTP error: ${e.message()}"))
            } catch (e: SSLHandshakeException) {
                //handle SSL handshake errors
                emit(Resource.Error("SSL handshake error: ${e.message}"))
            } catch (e: UnknownHostException) {
                //handle unknown host errors
                emit(Resource.Error("Unknown host: ${e.message}"))
            } catch (e: SocketTimeoutException) {
                //handle socket timeout errors
                emit(Resource.Error("Socket timeout: ${e.message}"))
            } catch (e: IOException) {
                //handle IOException (network issues)
                emit(Resource.Error("Network error. Please check your internet connection."))
            } catch (e: Exception) {
                //handle generic exception
                emit(Resource.Error("An error occurred during registration."))
            }
            emit(Resource.Loading(loading = false))
        }
    }
}