package com.example.homework_17.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_17.common.Resource
import com.example.homework_17.network.Network
import com.example.homework_17.service.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

class RegisterViewModel : ViewModel() {

    //current state of the register operation
    private val _registerResult = MutableStateFlow<Resource<RegisterResponse>>(Resource.Loading(loading = false))
    val registerResult: StateFlow<Resource<RegisterResponse>> get() = _registerResult

    //retrofit service for making API calls
    private val apiService = Network.create(ApiService::class.java)

    //function to initiate register process
    fun register(email: String, password: String) {
        viewModelScope.launch {
            _registerResult.value = Resource.Loading(loading = true)
            try {
                val response = apiService.register(RegisterRequest(email, password))
                _registerResult.value = Resource.Success(response)
            } catch (e: HttpException) {
                //handle HTTP error responses
                _registerResult.value = Resource.Error("HTTP error: ${e.message()}")
            } catch (e: SSLHandshakeException) {
                //handle SSL handshake errors
                _registerResult.value = Resource.Error("SSL handshake error: ${e.message}")
            } catch (e: UnknownHostException) {
                //handle unknown host errors
                _registerResult.value = Resource.Error("Unknown host: ${e.message}")
            } catch (e: SocketTimeoutException) {
                //handle socket timeout errors
                _registerResult.value = Resource.Error("Socket timeout: ${e.message}")
            } catch (e: IOException) {
                //handle IOException (network issues)
                _registerResult.value = Resource.Error("Network error. Please check your internet connection.")
            } catch (e: Exception) {
                //handle generic exception
                _registerResult.value = Resource.Error("An error occurred during registration.")
            }
            _registerResult.value = Resource.Loading(loading = false)
        }
    }
}

