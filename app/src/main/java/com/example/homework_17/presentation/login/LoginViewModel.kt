package com.example.homework_17.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_17.data.common.Resource
import com.example.homework_17.datastore.DataStoreUtil
import com.example.homework_17.domain.login.LoginRepository
import com.example.homework_17.domain.login.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class LoginViewModel : ViewModel() {
//
//    //current state of the login operation
//    private val _loginResult = MutableStateFlow<Resource<LoginResponse>>(Resource.Loading(loading = false))
//    val loginResult: StateFlow<Resource<LoginResponse>> get() = _loginResult
//
//    //retrofit service for making API calls
//    private val apiService = Network.createLoginService(LoginService::class.java)
//
//    //function to initiate login process
//    fun login(email: String, password: String, context: Context) {
//        viewModelScope.launch {
//            _loginResult.value = Resource.Loading(loading = true)
//            try {
//                val response = apiService.login(LoginRequest(email, password))
//                _loginResult.value = Resource.Success(response)
//                DataStoreUtil.saveUserEmail(email)
//            } catch (e: HttpException) {
//                //handle HTTP error responses
//                _loginResult.value = Resource.Error("HTTP error: ${e.message()}")
//            } catch (e: SSLHandshakeException) {
//                //handle SSL handshake errors
//                _loginResult.value = Resource.Error("SSL handshake error: ${e.message}")
//            } catch (e: UnknownHostException) {
//                //handle unknown host errors
//                _loginResult.value = Resource.Error("Unknown host: ${e.message}")
//            } catch (e: SocketTimeoutException) {
//                //handle socket timeout errors
//                _loginResult.value = Resource.Error("Socket timeout: ${e.message}")
//            } catch (e: IOException) {
//                //handle IOException (network issues)
//                _loginResult.value = Resource.Error("Network error. Please check your internet connection.")
//            } catch (e: Exception) {
//                //handle generic exception
//                _loginResult.value = Resource.Error("An error occurred during login.")
//            }
//            _loginResult.value = Resource.Loading(loading = false)
//        }
//    }
//    //function to save the user email in dataStore
//    fun saveUserEmailToDataStore(email: String) {
//        viewModelScope.launch {
//            DataStoreUtil.saveUserEmail(email)
//        }
//    }
//    //function to save the user token in dataStore
//    fun saveUserTokenToDataStore(token: String?) {
//        viewModelScope.launch {
//            DataStoreUtil.saveUserToken(token ?: "")
//        }
//    }
//}
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginResult = MutableStateFlow<Resource<LoginResponse>>(Resource.Loading(loading = false))
    val loginResult: StateFlow<Resource<LoginResponse>> get() = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(email, password).collect{
                when(it) {
                    is Resource.Success -> _loginResult.value = Resource.Success(data = it.data!!)
                    is Resource.Error -> _loginResult.value = Resource.Error(errorMessage = it.errorMessage!!)
                    is Resource.Loading -> _loginResult.value = Resource.Loading(loading = it.loading)
                }
            }
        }
    }

    // Other functions in your ViewModel remain the same

    fun saveUserEmailToDataStore(email: String) {
        viewModelScope.launch {
            DataStoreUtil.saveUserEmail(email)
        }
    }

    fun saveUserTokenToDataStore(token: String?) {
        viewModelScope.launch {
            DataStoreUtil.saveUserToken(token ?: "")
        }
    }
}
