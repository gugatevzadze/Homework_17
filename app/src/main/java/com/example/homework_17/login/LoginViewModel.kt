package com.example.homework_17.login

import android.content.Context
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
import javax.net.ssl.SSLHandshakeException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class LoginViewModel : ViewModel() {

    //current state of the login operation
    private val _loginResult = MutableStateFlow<Resource<LoginResponse>>(Resource.Loading(loading = false))
    val loginResult: StateFlow<Resource<LoginResponse>> get() = _loginResult

    //retrofit service for making API calls
    private val apiService = Network.create(ApiService::class.java)

    //function to initiate login process
    fun login(email: String, password: String, context: Context) {
        viewModelScope.launch {
            _loginResult.value = Resource.Loading(loading = true)
            try {
                val response = apiService.login(LoginRequest(email, password))
                _loginResult.value = Resource.Success(response)
                saveUserEmailToSharedPreferences(email, context)
            } catch (e: HttpException) {
                //handle HTTP error responses
                _loginResult.value = Resource.Error("HTTP error: ${e.message()}")
            } catch (e: SSLHandshakeException) {
                //handle SSL handshake errors
                _loginResult.value = Resource.Error("SSL handshake error: ${e.message}")
            } catch (e: UnknownHostException) {
                //handle unknown host errors
                _loginResult.value = Resource.Error("Unknown host: ${e.message}")
            } catch (e: SocketTimeoutException) {
                //handle socket timeout errors
                _loginResult.value = Resource.Error("Socket timeout: ${e.message}")
            } catch (e: IOException) {
                //handle IOException (network issues)
                _loginResult.value = Resource.Error("Network error. Please check your internet connection.")
            } catch (e: Exception) {
                //handle generic exception
                _loginResult.value = Resource.Error("An error occurred during login.")
            }
            _loginResult.value = Resource.Loading(loading = false)
        }
    }
    //function to save the user email to SharedPreferences
    private fun saveUserEmailToSharedPreferences(email: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("user_email", email)
            apply()
        }
    }
}


