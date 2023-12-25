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

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginResult =
        MutableStateFlow<Resource<LoginResponse>>(Resource.Loading(loading = false))
    val loginResult: StateFlow<Resource<LoginResponse>> get() = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(email, password).collect {
                when (it) {
                    is Resource.Success -> _loginResult.value = Resource.Success(data = it.data!!)
                    is Resource.Error -> _loginResult.value =
                        Resource.Error(errorMessage = it.errorMessage!!)

                    is Resource.Loading -> _loginResult.value =
                        Resource.Loading(loading = it.loading)
                }
            }
        }
    }

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
